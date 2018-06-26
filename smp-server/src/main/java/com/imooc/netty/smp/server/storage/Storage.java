package com.imooc.netty.smp.server.storage;

public interface Storage {

    <T> void setObject(String key, T object);

    <T> T getObject(String key);

    <T> T removeObject(String key);
}
