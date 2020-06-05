package com.zl.learReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName : interrupt
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-29 19:51
 */
@Slf4j
public class Interrupt {

    static volatile long count = 1000;

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread(()->{
            try {
                log.debug("尝试获得锁");
                lock.lockInterruptibly();
                log.debug("获得了锁");
                //log.debug("{}", count--);
            } catch (InterruptedException e) {
                log.debug("被打断", e);
                return;
            }

            lock.unlock();

        }, "t1");

        lock.lock();
        log.debug("获得了锁");

        t1.start();

        Thread.sleep(1000);

        t1.interrupt();
        log.debug("执行打断");

        Thread.sleep(1000);

        lock.unlock();
        log.debug("释放了锁");


        
    }
}
