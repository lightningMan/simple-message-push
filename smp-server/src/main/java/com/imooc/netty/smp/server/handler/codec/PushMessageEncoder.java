package com.imooc.netty.smp.server.handler.codec;

import com.imooc.netty.smp.common.protocol.PushMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import static com.imooc.netty.smp.common.Constants.*;

public class PushMessageEncoder extends MessageToByteEncoder<PushMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, PushMessage pushMessage, ByteBuf out) {
        int len = calcLengthOf(pushMessage);
        out.writeInt(len);
        out.writeBytes(pushMessage.getMessageId().getBytes(CHARSET));
        out.writeBytes(pushMessage.getContent().getBytes(CHARSET));
        out.writeLong(pushMessage.getTimestamp());
    }

    private int calcLengthOf(PushMessage pushMessage) {
        return LENGTH_OF_MESSAGE_ID + pushMessage.getContent().length() + LENGTH_OF_TIMESTAMP;
    }
}
