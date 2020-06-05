package com.zl.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

@Slf4j
public class SchedulePool {
    static AtomicInteger s = new AtomicInteger(0);

    public static void main(String[] args) {
        /*ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

        for (int i = 0; i < 10; i++) {
            service.schedule(()->{
                long start = System.currentTimeMillis();
                while(System.currentTimeMillis() - start < 1000){
                }
                log.debug("" + (s.getAndIncrement()));
            }, 1, TimeUnit.SECONDS);
        }

        service.schedule(()->{
            log.debug("22");
        }, 1, TimeUnit.SECONDS);*/

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        log.debug("start...");

        pool.scheduleAtFixedRate(() -> {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("running...");
        }, 1, 1, TimeUnit.SECONDS);		// 每隔1s打印一次running

    }
}
