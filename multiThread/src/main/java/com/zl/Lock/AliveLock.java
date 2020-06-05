package com.zl.Lock;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : AliveLock
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-29 17:14
 */
@Slf4j
public class AliveLock {
    static volatile int count = 10;

    static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            // 期望减到 0 退出循环
            while (count > 0) {
                try {
                    Thread.sleep(400);
                    count--;
                    log.debug("count: {}", count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "t1").start();

        new Thread(() -> {
            // 期望超过 20 退出循环
            while (count < 20) {
                try {
                    Thread.sleep(200);
                    count++;
                    log.debug("count: {}", count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }
}
