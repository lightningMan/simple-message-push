package com.imooc.netty.smp.common.protocol;

import lombok.Data;

@Data
public class PushAck {
    /**
     * 消息ID, UUID, 36个字节
     */
    private String messageId;

    /**
     * 收到消息时间戳，64字节
     */
    private Long timestamp;

}
