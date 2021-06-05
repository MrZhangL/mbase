package com.tomcode.agent;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DisprutorUse {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        LongEventFactory factory = new LongEventFactory();
        int buffSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, buffSize, executorService, ProducerType.MULTI, new YieldingWaitStrategy());

        disruptor.handleEventsWith(new LongEventHandler());

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        final EventTranslatorOneArg<LongEvent, Long> TRANSLATOR = (event, sequence, val) -> event.setValue(val);

        for(int i = 0; i < 10; i++) {
            /*long seq = ringBuffer.next();
            LongEvent longEvent = ringBuffer.get(seq);
            longEvent.setValue(10L);*/
            disruptor.publishEvent(TRANSLATOR, 10L);
        }

        Thread.sleep(1000);
        disruptor.shutdown();
        executorService.shutdown();
    }

    static class LongEventFactory implements EventFactory<LongEvent> {

        @Override
        public LongEvent newInstance() {
            return new LongEvent();
        }
    }

    static class LongEventHandler implements EventHandler<LongEvent> {
        public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
            System.out.println("[" + Thread.currentThread().getName() + "]" + "Event: " + event.getValue());
        }
    }

    static class LongEvent {
        private Long value;

        public Long getValue() {
            return value;
        }

        public void setValue(Long value) {
            this.value = value;
        }
    }
}
