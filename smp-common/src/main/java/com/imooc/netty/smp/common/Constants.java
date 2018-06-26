package com.imooc.netty.smp.common;

import java.nio.charset.Charset;

public interface Constants {
    Charset CHARSET = Charset.forName("UTF-8");

    int LENGTH_OF_MESSAGE_ID = 36;
    int LENGTH_OF_TIMESTAMP = 8;

}
