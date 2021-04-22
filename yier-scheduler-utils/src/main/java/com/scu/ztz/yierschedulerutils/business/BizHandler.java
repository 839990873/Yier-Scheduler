package com.scu.ztz.yierschedulerutils.business;

import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;

public interface BizHandler {
    public ReturnDatagram handle(Datagram datagram) throws Exception;
}
