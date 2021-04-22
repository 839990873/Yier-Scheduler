package com.scu.ztz.yierschedulerboss.server;

import com.scu.ztz.yierschedulerboss.YierBossConfig;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.CommonServiceEnums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class BossServerHandler extends SimpleChannelInboundHandler<String> {
    protected static Logger logger = LoggerFactory.getLogger(BossServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Datagram datagram = Datagram.fromJson(msg);
        String datagramType = datagram.getType();
        String datagramJson = datagram.getJSON();
        ReturnDatagram returnDatagram = null;
        if (YierBossConfig.commonServiceEnums.match(datagramType) != null) {
            if(datagramType.equals(CommonServiceEnums.RETURN)){
                YierBossConfig.getReturndatagramqueue().add(ReturnDatagram.fromJson(datagramJson));
            }
            else {
                logger.warn("Unhandled commonServiceEnums:" + datagramType);
            }
        } else if ((YierBossConfig.bossServiceEnums.match(datagramType)) != null) {
            returnDatagram = YierBossConfig.getBoosServiceProvider().handle(datagram);
        } else {
            logger.warn("Wrong Datagram type:" + datagramType);
        }
        // 写回返回值
        if (returnDatagram != null)
            ctx.writeAndFlush(returnDatagram.toDatagram().toJsonWithDelimiter());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // todo
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // todo
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

}