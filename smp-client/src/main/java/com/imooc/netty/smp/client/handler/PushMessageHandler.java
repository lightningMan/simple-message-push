package com.imooc.netty.smp.client.handler;

import com.imooc.netty.smp.common.protocol.PushAck;
import com.imooc.netty.smp.common.protocol.PushMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class PushMessageHandler extends SimpleChannelInboundHandler<PushMessage> {
    private static final Logger logger = LoggerFactory.getLogger(PushMessageHandler.class);

    public static final PushMessageHandler INSTANCE = new PushMessageHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PushMessage msg) {
        logger.info("receive message, content: {}", msg.getContent());

        PushAck pushAck = new PushAck();
        pushAck.setMessageId(msg.getMessageId());
        pushAck.setTimestamp(System.currentTimeMillis());

        ctx.channel().writeAndFlush(pushAck);
    }
}
