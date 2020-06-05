package com.zl.threadpool;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class PoolTest {

    static AtomicInteger s;

    public static void main(String[] args) throws InterruptedException {
        

        Pool pool = new Pool(5, 10);
        s = new AtomicInteger(1000);
        for (int i = 0; i < 20; i++) {
            pool.submit(()->{
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                s.updateAndGet(val -> val - 50);
                log.debug("s:" + s.get());
            });
        }


        Thread.sleep(60*1000);

        System.out.println(s);

    }
}
