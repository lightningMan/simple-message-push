package com.imooc.netty.smp.common.protocol;


import lombok.Data;

@Data
public class PushMessage {
    /**
     * 消息ID, UUID, 36个字节
     */
    private String messageId;

    /**
     * 消息内容，可变长字节
     */
    private String content;

    /**
     * 消息产生时间戳，8字节
     */
    private Long timestamp;
}
