package com.imooc.netty.smp.client.handler.codec;

import com.imooc.netty.smp.common.Constants;
import com.imooc.netty.smp.common.protocol.PushAck;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PushAckEncoder extends MessageToByteEncoder<PushAck> {
    @Override
    protected void encode(ChannelHandlerContext ctx, PushAck pushAck, ByteBuf out) {
        int length = Constants.LENGTH_OF_MESSAGE_ID + Constants.LENGTH_OF_TIMESTAMP;
        out.writeInt(length);
        out.writeBytes(pushAck.getMessageId().getBytes(Constants.CHARSET));
        out.writeLong(pushAck.getTimestamp());
    }
}
