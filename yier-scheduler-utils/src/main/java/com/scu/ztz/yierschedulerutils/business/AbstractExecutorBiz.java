package com.scu.ztz.yierschedulerutils.business;

import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.BeatDatagram;
// import com.scu.ztz.yierschedulerutils.datagram.executorService.GetLogDatagram;
// import com.scu.ztz.yierschedulerutils.datagram.executorService.KillDatagram;
import com.scu.ztz.yierschedulerutils.datagram.executorService.RunDatagram;

import io.netty.channel.Channel;

public abstract class AbstractExecutorBiz implements BizHandler {
    public abstract ReturnDatagram beat(BeatDatagram datagram, Channel c);

    // public abstract ReturnDatagram getLog(GetLogDatagram datagram, Channel c);

    // public abstract ReturnDatagram kill(KillDatagram datagram, Channel c);

    public abstract ReturnDatagram run(RunDatagram datagram, Channel c);

    @Override
    public abstract ReturnDatagram handle(Datagram datagram, Channel c) throws Exception;
}