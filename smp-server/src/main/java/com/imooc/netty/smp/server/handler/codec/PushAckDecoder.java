package com.imooc.netty.smp.server.handler.codec;

import com.imooc.netty.smp.common.Constants;
import com.imooc.netty.smp.common.protocol.PushAck;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PushAckDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        Object object = decode(in);
        if (object != null) {
            out.add(object);
        }
    }

    private Object decode(ByteBuf in) {
        PushAck pushAck = new PushAck();

        byte[] messageIdBytes = new byte[Constants.LENGTH_OF_MESSAGE_ID];
        in.readBytes(messageIdBytes);
        pushAck.setMessageId(new String(messageIdBytes, Constants.CHARSET));
        pushAck.setTimestamp(in.readLong());

        return pushAck;
    }
}
