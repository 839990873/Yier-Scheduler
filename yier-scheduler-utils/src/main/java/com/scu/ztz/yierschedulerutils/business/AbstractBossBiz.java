package com.scu.ztz.yierschedulerutils.business;

import com.scu.ztz.yierschedulerutils.DO.Executor;
import com.scu.ztz.yierschedulerutils.datagram.BossServiceDatagram;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.CallBackDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryRemoveDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;

import io.netty.channel.Channel;

// Boss服务的抽象，
// 在Executor中建立BossClientHandler对象，在各个业务函数中向channel写入对应的请求，并等待请求结果。
// 在Boss中建立BossServerHandler对象，处理对应的请求并返回请求结果。
public abstract class AbstractBossBiz implements BizHandler {
    public abstract ReturnDatagram registry(RegistryDatagram datagram, Channel c);

    public abstract ReturnDatagram registryRemove(RegistryRemoveDatagram datagram, Channel c);

    public abstract ReturnDatagram callback(CallBackDatagram datagram, Channel c);

    @Override
    public abstract ReturnDatagram handle(Datagram datagram, Channel c) throws Exception;

}
