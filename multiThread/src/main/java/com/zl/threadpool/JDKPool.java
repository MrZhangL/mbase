package com.zl.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

@Slf4j
public class JDKPool {

    static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    public static void test1(){
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<String> future = service.submit(() -> {
            sleep(1000);
            int i = 1/0;
            log.debug("1");
            return "1";
        });

        log.debug("提交任务成功");

        /*try {
            log.debug(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

    }

    public static void test2() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        List<Future<String>> futures = service.invokeAll(Arrays.asList(
                () -> {
                    sleep(1000);
                    log.debug("1");
                    return "1";
                },
                () -> {
                    sleep(500);
                    log.debug("2");
                    return "2";
                },
                () -> {
                    sleep(2000);
                    log.debug("3");
                    return "3";
                }
        ));
        log.debug("提交完成");

        futures.forEach(f -> {
            try {
                log.debug(f.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testFixed(){
        ExecutorService service = Executors.newFixedThreadPool(2, new ThreadFactory() {
            AtomicInteger threadID = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "mypool_t" + threadID.getAndIncrement());
            }
        });

        service.execute(()-> log.debug("" + i++));
        service.execute(()-> log.debug("" + i++));
        service.execute(()-> log.debug("" + i++));
    }

    @Test
    public void testSingle(){
        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(()-> {
            int i = 1 / 0;
            log.debug("1");
        });

        service.execute(()-> {
            log.debug("2");
        });

        service.execute(()-> {
            log.debug("3");
        });
    }

    @Test
    public void testSy() throws InterruptedException {
        SynchronousQueue<Integer> integers = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                log.debug("putting {} ", 1);
                integers.put(1);
                log.debug("{} putted...", 1);
                log.debug("putting...{} ", 2);
                integers.put(2);
                log.debug("{} putted...", 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();
        sleep(1000);
        new Thread(() -> {
            try {
                log.debug("taking {}", 1);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();
        sleep(1000);
        new Thread(() -> {
            try {
                log.debug("taking {}", 2);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t3").start();
    }
}
