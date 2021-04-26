package com.scu.ztz.yierschedulerutils.business;

import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;

import io.netty.channel.Channel;

public interface BizHandler {
    public ReturnDatagram handle(Datagram datagram,Channel channel) throws Exception;
}
