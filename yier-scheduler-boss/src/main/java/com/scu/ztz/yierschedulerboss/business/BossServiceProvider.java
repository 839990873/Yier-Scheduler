package com.scu.ztz.yierschedulerboss.business;

import com.scu.ztz.yierschedulerutils.business.AbstractBossBiz;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.CallBackDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryRemoveDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BossServiceProvider extends AbstractBossBiz {

    private static Logger logger = LoggerFactory.getLogger(BossServiceProvider.class);

    @Override
    public ReturnDatagram registry(RegistryDatagram datagram) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReturnDatagram registryRemove(RegistryRemoveDatagram datagram) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReturnDatagram callback(CallBackDatagram datagram) {
        
        return null;
    }

    // BossService的服务没有返回报文。
    @Override
    public ReturnDatagram handle(Datagram datagram) {
        var type = datagram.getType();
        switch (type) {
        case BossServiceEnums.REGISTRY:
            return registry(RegistryDatagram.fromJson(datagram.getJSON()));
        case BossServiceEnums.REGISTRY_REMOVE:
            return registryRemove(RegistryRemoveDatagram.fromJson(datagram.getJSON()));
        case BossServiceEnums.CALLBACK:
            return callback(CallBackDatagram.fromJson(datagram.getJSON()));
        default:
            logger.warn("Wrong Datagram type:"+type);
            return null;
        }
    }

}