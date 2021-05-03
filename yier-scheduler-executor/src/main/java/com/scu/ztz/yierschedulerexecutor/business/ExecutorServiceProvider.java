package com.scu.ztz.yierschedulerexecutor.business;

import java.sql.Timestamp;
import java.util.concurrent.ThreadPoolExecutor;

import com.scu.ztz.yierschedulerexecutor.YierExecutorConfig;
import com.scu.ztz.yierschedulerutils.DO.JobInfo;
import com.scu.ztz.yierschedulerutils.business.AbstractExecutorBiz;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.BeatDatagram;
// import com.scu.ztz.yierschedulerutils.datagram.executorService.GetLogDatagram;
// import com.scu.ztz.yierschedulerutils.datagram.executorService.KillDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.RunDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.ExecutorServiceEnums;
import com.scu.ztz.yierschedulerutils.glue.GlueFactory;
import com.scu.ztz.yierschedulerutils.utils.JobRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

public class ExecutorServiceProvider extends AbstractExecutorBiz {
    private static Logger logger = LoggerFactory.getLogger(ExecutorServiceProvider.class);
    private ThreadPoolExecutor bizThreadPool = YierExecutorConfig.bizThreadPool;

    @Override
    public ReturnDatagram beat(BeatDatagram datagram, Channel c) {
        return null;
    }

    // @Override
    // public ReturnDatagram getLog(GetLogDatagram datagram, Channel c) {
    // return null;
    // }

    // @Override
    // public ReturnDatagram kill(KillDatagram datagram, Channel c) {
    // return null;
    // }

    @Override
    public ReturnDatagram run(RunDatagram datagram, Channel c) {
        int i = datagram.getJobId();
        JobInfo jobInfo = YierExecutorConfig.getExecutorConfig().getJobInfoDao().getJobInfo(i);
        int logID = datagram.getLogId();
        logger.info("任务：id={},name ={},logid={} 已经开始执行", i, datagram.getJobName(), logID);
        bizThreadPool.execute(() -> {
            var logDao=YierExecutorConfig.getExecutorConfig().getJobLogDao();
            try {
                logDao.newHandle(logID,
                        new Timestamp(System.currentTimeMillis()));
                JobRunner jr = GlueFactory.getInstance().loadNewInstance(jobInfo.getGlueSourceCode());
                jr.run();
                logDao.setHandleStatus(logID, "SUCCESS", "调度成功");
            } catch (Exception e) {
                logger.info("执行Glue错误:" + e.getMessage());
                logDao.setHandleStatus(logID, "FAILED", "调度失败:"+e.getMessage());
            }
        });
        var ret = new ReturnDatagram(ReturnDatagram.SUCCESS_CODE,
                "触发成功,jobID:" + Integer.toString(i) + "logID:" + logID);
        return ret;
    }

    @Override
    public ReturnDatagram handle(Datagram datagram, Channel c) throws Exception {
        var type = datagram.getType();
        switch (type) {
            case ExecutorServiceEnums.BEAT:
                return beat(BeatDatagram.fromJson(datagram.getJSON()), c);
            case ExecutorServiceEnums.RUN:
                return run(RunDatagram.fromJson(datagram.getJSON()), c);
            default:
                logger.warn("Wrong Datagram type:" + type);
                return null;
        }
    }

}
