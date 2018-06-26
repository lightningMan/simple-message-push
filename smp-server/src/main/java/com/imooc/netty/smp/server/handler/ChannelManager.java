package com.imooc.netty.smp.server.handler;

import com.imooc.netty.smp.common.protocol.PushMessage;
import com.imooc.netty.smp.server.storage.Storage;
import com.imooc.netty.smp.server.storage.impl.StorageMemoryImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.ThreadLocalRandom;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class ChannelManager extends ChannelInboundHandlerAdapter {
    public static final ChannelManager INSTANCE = new ChannelManager();
    private Storage messageStorage = StorageMemoryImpl.INSTANCE;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int delay = random.nextInt(5, 10);

        // 每一条新连接，都是5~10秒之后发消息
        ctx.executor().scheduleAtFixedRate(() -> {
            PushMessage pushMessage = new PushMessage();
            pushMessage.setMessageId(UUID.randomUUID().toString());
            pushMessage.setContent("hello,world!");
            pushMessage.setTimestamp(System.currentTimeMillis());
            messageStorage.setObject(pushMessage.getMessageId(), pushMessage);
            ctx.channel().writeAndFlush(pushMessage);
        }, delay, delay, TimeUnit.SECONDS);

    }
}
