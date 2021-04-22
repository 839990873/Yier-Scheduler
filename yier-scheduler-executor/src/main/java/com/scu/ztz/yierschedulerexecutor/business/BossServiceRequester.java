package com.scu.ztz.yierschedulerexecutor.business;

import com.scu.ztz.yierschedulerutils.business.AbstractBossBiz;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.CallBackDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryRemoveDatagram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BossServiceRequester extends AbstractBossBiz {

    private static Logger logger = LoggerFactory.getLogger(BossServiceRequester.class);

    @Override
    public ReturnDatagram registry(RegistryDatagram datagram) {
        return null;
    }

    @Override
    public ReturnDatagram registryRemove(RegistryRemoveDatagram datagram) {
        return null;
    }

    @Override
    public ReturnDatagram callback(CallBackDatagram datagram) { 
        return null;
    }

    
    @Override
    public ReturnDatagram handle(Datagram datagram) throws Exception{
        throw new Exception("不许调用");
    }

}