package com.scu.ztz.yierschedulerexecutor.server;

import java.io.ObjectInputFilter.Config;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.scu.ztz.yierschedulerexecutor.YierExecutorConfig;
import com.scu.ztz.yierschedulerutils.datagram.Datagram;
import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.datagram.bossService.RegistryDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.CommonServiceEnums;
import com.scu.ztz.yierschedulerexecutor.business.ExecutorServiceProvider;
import com.scu.ztz.yierschedulerexecutor.business.BossServiceRequester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ExecutorServerHandler extends SimpleChannelInboundHandler<String> {
    protected static Logger logger = LoggerFactory.getLogger(ExecutorServerHandler.class);
    private ExecutorServiceProvider executorServiceProvider = YierExecutorConfig.getExecutorServiceProvider();
    private BossServiceRequester bossServiceRequester = YierExecutorConfig.getBossServiceRequester();
    private ArrayBlockingQueue<ReturnDatagram> queue = YierExecutorConfig.getReturndatagramqueue();
    private ThreadPoolExecutor bizThreadPool = YierExecutorConfig.bizThreadPool;
    private int timeout = YierExecutorConfig.MAX_TIMEOUT;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Datagram datagram = Datagram.fromJson(msg);
        String datagramType = datagram.getType();
        String datagramJson = datagram.getJSON();
        if (YierExecutorConfig.commonServiceEnums.match(datagramType) != null) {
            if (datagramType.equals(CommonServiceEnums.RETURN)) {
                queue.add(ReturnDatagram.fromJson(datagramJson));
            } else {
                logger.warn("Unhandled commonServiceEnums:" + datagramType);
            }
        } else if ((YierExecutorConfig.executorServiceEnums.match(datagramType)) != null) {
            try {
                var ret = executorServiceProvider.handle(datagram, ctx.channel());
                ctx.channel().writeAndFlush(ret.toDatagram().toJsonWithDelimiter());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("Wrong Datagram type:" + datagramType);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 执行注册任务
        // 初始化Address,唯一可以修改localAddress的地方
        YierExecutorConfig.localAddress = ctx.channel().localAddress().toString();
        RegistryDatagram rgDatagram = new RegistryDatagram(YierExecutorConfig.localAddress);
        bizThreadPool.execute(() -> {
            try {
                var ret = bossServiceRequester.registry(rgDatagram, ctx.channel());
                logger.info(ret.getMsg());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // todo
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

}