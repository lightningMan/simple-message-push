package com.imooc.netty.smp.client.handler.codec;

import com.imooc.netty.smp.common.Constants;
import com.imooc.netty.smp.common.protocol.PushMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PushMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        Object decoded = decode(in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    private Object decode(ByteBuf in) {
        PushMessage pushMessage = new PushMessage();

        byte[] messageIdBytes = new byte[Constants.LENGTH_OF_MESSAGE_ID];
        in.readBytes(messageIdBytes);
        pushMessage.setMessageId(new String(messageIdBytes, Constants.CHARSET));

        byte[] contentBytes = new byte[in.readableBytes() - Constants.LENGTH_OF_TIMESTAMP];
        in.readBytes(contentBytes);
        pushMessage.setContent(new String(contentBytes, Constants.CHARSET));

        pushMessage.setTimestamp(in.readLong());

        return pushMessage;
    }
}
