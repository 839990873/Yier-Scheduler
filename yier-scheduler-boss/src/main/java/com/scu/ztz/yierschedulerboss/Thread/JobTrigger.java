package com.scu.ztz.yierschedulerboss.Thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.scu.ztz.yierschedulerboss.YierBossConfig;
import com.scu.ztz.yierschedulerboss.business.ExecutorServiceRequester;
import com.scu.ztz.yierschedulerboss.router.RouteStrategyEnum;
import com.scu.ztz.yierschedulerboss.router.Router;
import com.scu.ztz.yierschedulerutils.DO.JobInfo;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.RunDatagram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobTrigger {
    private ThreadPoolExecutor triggerPool = null;
    private static Logger logger = LoggerFactory.getLogger(JobTrigger.class);

    private JobTrigger() {
    };

    private static JobTrigger instance = new JobTrigger();

    public static JobTrigger getInstance() {
        return instance;
    }

    public void start() {
        triggerPool = new ThreadPoolExecutor(10, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "jobTrigger" + r.hashCode());
                    }
                });
        logger.info("启动任务触发线程");
    }

    public void stop() {
        triggerPool.shutdownNow();
        logger.info("关闭任务触发线程");
    }

    public void addTrigger(JobInfo jobInfo) {
        triggerPool.execute(() -> {
            ExecutorServiceRequester requester = YierBossConfig.getExecutorServiceRequester();
            Router router = RouteStrategyEnum.match(jobInfo.getRouteStrategy()).getRouter();
            String address = router.route();
            if (address == null) { 
                logger.error("未找到执行器地址:" + jobInfo.toString());
                return;
            }
            // 设置rDatagram
            RunDatagram rDatagram = new RunDatagram();
            rDatagram.setExeAdd(address);
            rDatagram.setJobId(jobInfo.getId());
            rDatagram.setJobName(jobInfo.getJobName());
            rDatagram.setGlueSource(jobInfo.getGlueSource());
            rDatagram.setGlueProerties(jobInfo.getGlueProerties());
            rDatagram.setExecutorParam(jobInfo.getExecutorParam());
            var ret = requester.run(rDatagram, YierBossConfig.getChannel(address));
            if (ret.getCode() == ReturnDatagram.FAIL_CODE) {
                logger.error("触发任务失败:jobid = {}, jobName = {}, executor = {}", rDatagram.getJobId(),
                        rDatagram.getJobName(), rDatagram.getExeAdd());
            } else if (ret.getCode() == ReturnDatagram.SUCCESS_CODE) {
                logger.error("触发任务成功:jobid = {}, jobName = {}, executor = {}", rDatagram.getJobId(),
                        rDatagram.getJobName(), rDatagram.getExeAdd());
            }
        });
    }
}
