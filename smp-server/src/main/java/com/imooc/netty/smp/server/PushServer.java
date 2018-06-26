package com.imooc.netty.smp.server;

import com.imooc.netty.smp.common.handler.spliter.Spliter;
import com.imooc.netty.smp.server.handler.ChannelManager;
import com.imooc.netty.smp.server.handler.PushAckHandler;
import com.imooc.netty.smp.server.handler.codec.PushAckDecoder;
import com.imooc.netty.smp.server.handler.codec.PushMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushServer {
    private static final int PORT = 8000;

    private static final Logger logger = LoggerFactory.getLogger(PushServer.class);

    public static void main(String[] args) {
        PushServer pushServer = new PushServer();
        pushServer.start();
    }

    private void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        serverBootstrap.group(boosGroup, workGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            protected void initChannel(NioSocketChannel ch) {
//                ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                ch.pipeline().addLast(ChannelManager.INSTANCE);
                ch.pipeline().addLast(new Spliter());
                ch.pipeline().addLast(new PushAckDecoder());
                ch.pipeline().addLast(new PushMessageEncoder());
                ch.pipeline().addLast(PushAckHandler.INSTANCE);
//                logger.info("new connection: " + ch.remoteAddress());
            }
        });

        serverBootstrap.bind(PORT).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                logger.info("bind port {} success!", PORT);
            } else {
                logger.error("bind port {} fail!", PORT);
            }
        });
    }
}
