package com.tomcode.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

public class MultiDisruptor {

    static AtomicLong cnt = new AtomicLong();

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        RingBuffer<LongEvent> ringBuffer = RingBuffer.create(ProducerType.MULTI, ()-> new LongEvent(cnt.getAndIncrement()),
                1024*1024, new YieldingWaitStrategy());

        SequenceBarrier barrier = ringBuffer.newBarrier();

        CountDownLatch countDownLatch = new CountDownLatch(100000);

        WorkHandler<LongEvent>[] consumers = new WorkHandler[16];
        for (int i = 0; i < consumers.length; i++) {
            final int id = i;
            consumers[i] = longEvent -> {
                longEvent.getValue();//System.out.println("消费者id:" + id + ", 消费了" + longEvent.getValue());
                countDownLatch.countDown();
            };
        }

        WorkerPool<LongEvent> workerPool = new WorkerPool<>(ringBuffer, barrier, new LongEventExceptionHandler(), consumers);

        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        workerPool.start(executorService);

        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            final Producer producer = new Producer(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        for (int j = 0; j < 1000; j++) {
                            producer.publishData();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        Thread.sleep(200);
        latch.countDown();

        long time = System.currentTimeMillis();

        countDownLatch.await();

        System.out.println(System.currentTimeMillis() - time);

        executorService.shutdown();

        Deque<Integer> dq = new LinkedList<>();
        dq.push(1);
        dq.pop();
        dq.poll();
        
    }

    static class Producer {
        private RingBuffer<LongEvent> ringBuffer;

        public Producer(RingBuffer<LongEvent> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        public void publishData() {
            long seq = ringBuffer.next();
            ringBuffer.publish(seq);
        }
    }

    static class LongEventExceptionHandler implements ExceptionHandler {

        @Override
        public void handleEventException(Throwable throwable, long l, Object o) {

        }

        @Override
        public void handleOnStartException(Throwable throwable) {

        }

        @Override
        public void handleOnShutdownException(Throwable throwable) {

        }
    }
}
