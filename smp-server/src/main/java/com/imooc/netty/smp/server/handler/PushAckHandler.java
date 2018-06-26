package com.imooc.netty.smp.server.handler;

import com.imooc.netty.smp.common.protocol.PushAck;
import com.imooc.netty.smp.common.protocol.PushMessage;
import com.imooc.netty.smp.server.storage.Storage;
import com.imooc.netty.smp.server.storage.impl.StorageMemoryImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@ChannelHandler.Sharable
public class PushAckHandler extends SimpleChannelInboundHandler<PushAck> {
    private Storage messageStorage = StorageMemoryImpl.INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(PushAckHandler.class);
    public static final PushAckHandler INSTANCE = new PushAckHandler();
    private AtomicBoolean init = new AtomicBoolean(false);

    private AtomicLong totalTime = new AtomicLong();
    private AtomicLong totalAck = new AtomicLong();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PushAck pushAck) {
        if (init.compareAndSet(false, true)) {
            startStaticsThread();
        }

        PushMessage pushMessage = messageStorage.removeObject(pushAck.getMessageId());
        totalAck.incrementAndGet();
        totalTime.addAndGet(pushAck.getTimestamp() - pushMessage.getTimestamp());
//        logger.info("receive ack, messageId: {}", pushAck.getMessageId());
    }

    private void startStaticsThread() {
        new Thread(this::statics).start();

    }

    private void quietSleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    private void statics() {
        while (!Thread.interrupted()) {
            long preTotalAck = totalAck.get();
            quietSleep();
            logger.info("qps: {}, avg time: {}ms", totalAck.get() - preTotalAck, ((float) totalTime.get()) / totalAck.get());
        }
    }
}
