package com.imooc.netty.smp.client;

import com.imooc.netty.smp.client.handler.PushMessageHandler;
import com.imooc.netty.smp.client.handler.codec.PushAckEncoder;
import com.imooc.netty.smp.client.handler.codec.PushMessageDecoder;
import com.imooc.netty.smp.common.handler.spliter.Spliter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushClient {
    private static final Logger logger = LoggerFactory.getLogger(PushClient.class);

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) throws Exception {
        PushClient pushClient = new PushClient();
        pushClient.start();
    }

    private void start() throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            protected void initChannel(NioSocketChannel ch) {
//                ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                ch.pipeline().addLast(new Spliter());
                ch.pipeline().addLast(new PushMessageDecoder());
                ch.pipeline().addLast(new PushAckEncoder());
                ch.pipeline().addLast(PushMessageHandler.INSTANCE);
            }
        });

        while (!Thread.interrupted()) {
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT);
            channelFuture.get();
            if (!channelFuture.isSuccess()) {
                logger.error("something has wrong, bye!");
                break;
            }

            Thread.sleep(50);
//            bootstrap.connect(HOST, PORT).addListener((ChannelFutureListener) future -> {
//            if (future.isSuccess()) {
//                logger.info("success connected to server!");
//            } else {
//                logger.error("fail to connect to server!");
//            }
//            });
        }


    }
}
