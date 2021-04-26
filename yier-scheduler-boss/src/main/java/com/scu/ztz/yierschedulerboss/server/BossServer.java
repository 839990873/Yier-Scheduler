package com.scu.ztz.yierschedulerboss.server;

import java.util.concurrent.ConcurrentHashMap;

import com.scu.ztz.yierschedulerutils.enums.DelimiterEnum;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BossServer {

    protected static Logger logger = LoggerFactory.getLogger(BossServer.class);

    private static BossServer instance = new BossServer();
    private Thread bossServerThread = new Thread(() -> {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(10);
        
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel ch) throws Exception {
                            var pipeline = ch.pipeline();
                            ByteBuf delimiter = Unpooled
                                    .copiedBuffer(DelimiterEnum.SERVICE_DELIMITER.getDelimiter().getBytes());
                            pipeline.addLast("framer", new DelimiterBasedFrameDecoder(1024*1024, delimiter));
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast("handler", new BossServerHandler());
                        };
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("BossServer启动失败", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }) ;

    public static BossServer getInstance() {
        return instance;
    }
    
    
    public void init() throws InterruptedException {
       bossServerThread.start();
    }
}