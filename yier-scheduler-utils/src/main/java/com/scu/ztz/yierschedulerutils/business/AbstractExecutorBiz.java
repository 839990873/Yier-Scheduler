package com.scu.ztz.yierschedulerutils.business;

import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.BeatDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.GetLogDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.KillDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.RunDatagram;

public abstract class AbstractExecutorBiz implements BizHandler{
    public abstract ReturnDatagram beat(BeatDatagram datagram);
    public abstract ReturnDatagram getLog(GetLogDatagram datagram);
    public abstract ReturnDatagram kill(KillDatagram datagram);
    public abstract ReturnDatagram run(RunDatagram datagram);
    public abstract ReturnDatagram handle(Datagram datagram) throws Exception;
}