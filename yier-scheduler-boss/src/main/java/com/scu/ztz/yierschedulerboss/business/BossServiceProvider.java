package com.scu.ztz.yierschedulerboss.business;

import com.scu.ztz.yierschedulerboss.YierBossConfig;
import com.scu.ztz.yierschedulerutils.DO.Executor;
import com.scu.ztz.yierschedulerutils.business.AbstractBossBiz;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.CallBackDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryRemoveDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.ReturnInstruction.Return;
import io.netty.channel.Channel;

public class BossServiceProvider extends AbstractBossBiz {

    private static Logger logger = LoggerFactory.getLogger(BossServiceProvider.class);

    @Override
    public ReturnDatagram registry(RegistryDatagram datagram, Channel c) {
        Executor executor = new Executor();
        executor.setExecutorAddress(datagram.getExecutorAddress());
        executor.setExecutorName(datagram.getName());
        executor.setLastUpdateTime(datagram.getCurrentTime());
        YierBossConfig.addExecutror(executor, c);
        return new ReturnDatagram(ReturnDatagram.SUCCESS_CODE, "注册成功");
    }

    @Override
    public ReturnDatagram registryRemove(RegistryRemoveDatagram datagram, Channel c) {
        return null;
    }

    @Override
    public ReturnDatagram callback(CallBackDatagram datagram, Channel c) {

        return null;
    }

    // BossService的服务没有返回报文。
    @Override
    public ReturnDatagram handle(Datagram datagram, Channel c) {
        var type = datagram.getType();
        switch (type) {
        case BossServiceEnums.REGISTRY:
            return registry(RegistryDatagram.fromJson(datagram.getJSON()), c);
        case BossServiceEnums.REGISTRY_REMOVE:
            return registryRemove(RegistryRemoveDatagram.fromJson(datagram.getJSON()), c);
        case BossServiceEnums.CALLBACK:
            return callback(CallBackDatagram.fromJson(datagram.getJSON()), c);
        default:
            logger.warn("Wrong Datagram type:" + type);
            return null;
        }
    }

}