package com.imooc.netty.smp.server.storage.impl;

import com.imooc.netty.smp.server.storage.Storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StorageMemoryImpl implements Storage {
    public static final Storage INSTANCE = new StorageMemoryImpl();

    private Map<String, Object> memory = new ConcurrentHashMap<>();

    @Override
    public <T> void setObject(String key, T object) {
        memory.put(key, object);
    }

    @Override
    public <T> T getObject(String key) {
        return (T) memory.get(key);
    }

    @Override
    public <T> T removeObject(String key) {
        return (T) memory.remove(key);
    }
}
