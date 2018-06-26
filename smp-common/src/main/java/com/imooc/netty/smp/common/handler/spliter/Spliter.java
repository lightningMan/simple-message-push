package com.imooc.netty.smp.common.handler.spliter;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int MAX_LENGTH = Integer.MAX_VALUE;

    private static final int LENGTH_FIELD_OFFSET = 0;

    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(MAX_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, 0, LENGTH_FIELD_LENGTH);
    }
}
