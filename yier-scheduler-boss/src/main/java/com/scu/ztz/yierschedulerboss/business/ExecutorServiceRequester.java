package com.scu.ztz.yierschedulerboss.business;

import com.scu.ztz.yierschedulerutils.business.AbstractExecutorBiz;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.BeatDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.GetLogDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.KillDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.RunDatagram;

public class ExecutorServiceRequester extends AbstractExecutorBiz{

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
        throw new Exception("不许调用");
    }
    
}
