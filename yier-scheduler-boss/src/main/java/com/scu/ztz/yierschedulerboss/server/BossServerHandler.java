package com.scu.ztz.yierschedulerboss.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.scu.ztz.yierschedulerboss.YierBossConfig;
import com.scu.ztz.yierschedulerboss.business.BossServiceProvider;
import com.scu.ztz.yierschedulerboss.business.ExecutorServiceRequester;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.CommonServiceEnums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class BossServerHandler extends SimpleChannelInboundHandler<String> {
    protected static Logger logger = LoggerFactory.getLogger(BossServerHandler.class);

    private BossServiceProvider boosServiceProvider = YierBossConfig.getBoosServiceProvider();
    private ExecutorServiceRequester executorServiceRequester = YierBossConfig.getExecutorServiceRequester();
    private ArrayBlockingQueue<ReturnDatagram> queue = YierBossConfig.getReturndatagramqueue();
    private ThreadPoolExecutor bizThreadPool = YierBossConfig.bizThreadPool;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Datagram datagram = Datagram.fromJson(msg);
        String datagramType = datagram.getType();
        String datagramJson = datagram.getJSON();
        if (YierBossConfig.commonServiceEnums.match(datagramType) != null) {
            // 接收返回值
            if (datagramType.equals(CommonServiceEnums.RETURN)) {
                queue.add(ReturnDatagram.fromJson(datagramJson));
            } else {
                logger.warn("Unhandled commonServiceEnums:" + datagramType);
            }
        } else if ((YierBossConfig.bossServiceEnums.match(datagramType)) != null) {
            // 处理Executor的请求
            bizThreadPool.execute(() -> {
                try {
                    var ret = boosServiceProvider.handle(datagram, ctx.channel());
                    ctx.channel().writeAndFlush(ret.toDatagram().toJsonWithDelimiter());
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            });
        } else {
            logger.warn("Wrong Datagram type:" + datagramType);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // System.out.println("新连接");
        // ctx.channel().writeAndFlush(new
        // ReturnDatagram().toDatagram().toJsonWithDelimiter());
        // Thread.sleep(1000);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

}