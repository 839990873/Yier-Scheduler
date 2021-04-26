package com.scu.ztz.yierschedulerexecutor.business;

import com.scu.ztz.yierschedulerutils.business.AbstractExecutorBiz;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.BeatDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.GetLogDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.KillDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.RunDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.ExecutorServiceEnums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

public class ExecutorServiceProvider extends AbstractExecutorBiz {
    private static Logger logger = LoggerFactory.getLogger(ExecutorServiceProvider.class);

    @Override
    public ReturnDatagram beat(BeatDatagram datagram, Channel c) {
        return null;
    }

    @Override
    public ReturnDatagram getLog(GetLogDatagram datagram, Channel c) {
        return null;
    }

    @Override
    public ReturnDatagram kill(KillDatagram datagram, Channel c) {
        return null;
    }

    @Override
    public ReturnDatagram run(RunDatagram datagram, Channel c) {
        //测试
        int i = datagram.getJobId();
        var ret = new ReturnDatagram(ReturnDatagram.SUCCESS_CODE, Integer.toString(i));
        return ret;
    }

    @Override
    public ReturnDatagram handle(Datagram datagram,Channel c) throws Exception {
        var type = datagram.getType();
        switch (type) {
        case ExecutorServiceEnums.BEAT:
            return beat(BeatDatagram.fromJson(datagram.getJSON()),c);
        case ExecutorServiceEnums.GET_LOG:
            return getLog(GetLogDatagram.fromJson(datagram.getJSON()),c);
        case ExecutorServiceEnums.KILL:
            return kill(KillDatagram.fromJson(datagram.getJSON()),c);
        case ExecutorServiceEnums.RUN:
            return run(RunDatagram.fromJson(datagram.getJSON()),c);
        default:
            logger.warn("Wrong Datagram type:" + type);
            return null;
        }
    }

}
