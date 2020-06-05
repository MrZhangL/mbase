package com.zl.learReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName : AlternantOutput
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-30 13:33
 */
@Slf4j
public class AlternantOutput {

    static int output = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(()->{
            lock.lock();
            try {
                for(int i = 0; i < 5; i++){
                    try {
                        while(output != 0){
                            condition.await();
                        }
                    } catch (InterruptedException e){
                        log.error("", e);
                        continue;
                    }
                    log.debug("a");
                    output = 1;
                    condition.signalAll();
                }
            } finally {
                lock.unlock();
            }

        }, "t1").start();

        new Thread(()->{
            lock.lock();
            try {
                for(int i = 0; i < 5; i++){
                    try {
                        while (output != 1) {
                            condition.await();
                        }
                    } catch (InterruptedException e) {
                        log.error("", e);
                        continue;
                    }
                    log.debug("b");
                    output = 2;
                    condition.signalAll();
                }
            }  finally {
                lock.unlock();
            }
        }, "t2").start();

        new Thread(()->{
            lock.lock();
            try {
                for(int i = 0; i < 5; i++){
                    try {
                        while(output != 2){
                            condition.await();
                        }
                    } catch (InterruptedException e) {
                        log.error("", e);
                        continue;
                    }

                    log.debug("c");
                    output = 0;
                    condition.signalAll();
                }
            } finally {
                lock.unlock();
            }
        }, "t3").start();
    }
}
