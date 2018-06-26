package com.imooc.netty.smp.server.handler;

import com.imooc.netty.smp.common.protocol.PushMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

@ChannelHandler.Sharable
public class ServerBusinessHandler extends ChannelInboundHandlerAdapter {
    public static final ServerBusinessHandler INSTANCE = new ServerBusinessHandler();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        PushMessage pushMessage = new PushMessage();
        pushMessage.setMessageId(UUID.randomUUID().toString());
        pushMessage.setContent("Hello,World!");
        pushMessage.setTimestamp(System.currentTimeMillis());
        ctx.channel().writeAndFlush(pushMessage);
    }
}
