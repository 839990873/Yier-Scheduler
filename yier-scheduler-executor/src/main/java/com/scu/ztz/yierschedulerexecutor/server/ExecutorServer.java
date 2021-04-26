package com.scu.ztz.yierschedulerexecutor.server;

import java.util.concurrent.ConcurrentHashMap;

import com.scu.ztz.yierschedulerexecutor.YierExecutorConfig;
import com.scu.ztz.yierschedulerutils.enums.DelimiterEnum;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorServer {

    protected static Logger logger = LoggerFactory.getLogger(ExecutorServer.class);

    private static ExecutorServer instance = new ExecutorServer();
    private static Thread executorServerThread = new Thread(() -> {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 得到pipeline
                    ChannelPipeline pipeline = ch.pipeline();
                    ByteBuf delimiter = Unpooled
                            .copiedBuffer(DelimiterEnum.SERVICE_DELIMITER.getDelimiter().getBytes());
                    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(1024 * 1024, delimiter));
                    pipeline.addLast("decoder", new StringDecoder());
                    pipeline.addLast("encoder", new StringEncoder());
                    pipeline.addLast("handler", new ExecutorServerHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap
                    .connect(YierExecutorConfig.getBossIP(), YierExecutorConfig.getBossPort()).sync();
            // 得到channel
            Channel channel = channelFuture.channel();
            logger.info("-------Executor：" + channel.localAddress() + "--------");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("Executor Server 启动失败", e);
        } finally {
            group.shutdownGracefully();
        }
    });

    public static ExecutorServer getInstance() {
        return instance;
    }

    public void init() throws InterruptedException {
        executorServerThread.start();
    }
}