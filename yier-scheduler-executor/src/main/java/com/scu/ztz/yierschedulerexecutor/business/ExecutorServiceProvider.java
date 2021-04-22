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

public class ExecutorServiceProvider extends AbstractExecutorBiz {
    private static Logger logger = LoggerFactory.getLogger(ExecutorServiceProvider.class);

    @Override
    public ReturnDatagram beat(BeatDatagram datagram) {
        return null;
    }

    @Override
    public ReturnDatagram getLog(GetLogDatagram datagram) {
        return null;
    }

    @Override
    public ReturnDatagram kill(KillDatagram datagram) {
        return null;
    }

    @Override
    public ReturnDatagram run(RunDatagram datagram) {
        return null;
    }

    @Override
    public ReturnDatagram handle(Datagram datagram) throws Exception {
        var type = datagram.getType();
        switch (type) {
        case ExecutorServiceEnums.BEAT:
            return beat(BeatDatagram.fromJson(datagram.getJSON()));
        case ExecutorServiceEnums.GET_LOG:
            return getLog(GetLogDatagram.fromJson(datagram.getJSON()));
        case ExecutorServiceEnums.KILL:
            return kill(KillDatagram.fromJson(datagram.getJSON()));
        case ExecutorServiceEnums.RUN:
            return run(RunDatagram.fromJson(datagram.getJSON()));
        default:
            logger.warn("Wrong Datagram type:" + type);
            return null;
        }
    }

}
