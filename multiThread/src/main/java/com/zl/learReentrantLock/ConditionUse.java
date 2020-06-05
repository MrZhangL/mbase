package com.zl.learReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName : ConditionUse
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-30 11:53
 */
@Slf4j
public class ConditionUse {

    private static final ReentrantLock room = new ReentrantLock();
    private static boolean hasCigarette = false;
    private static boolean hasFood = false;
    private static Condition cigarette = room.newCondition();
    private static Condition food = room.newCondition();

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            room.lock();

            try {
                while (!hasCigarette) {
                    log.debug("没有香烟，不干活");
                    cigarette.await();
                    log.debug("获得香烟，开始干活");
                }
            } catch (InterruptedException e) {
                log.error("",e);
                log.debug("被赶出房间");
                return;
            } finally {
                room.unlock();
            }
            log.debug("干活完成，退出房间");

        }, "小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                room.lock();
                try {
                    log.debug("干活");
                } finally {
                    room.unlock();
                }
            }, String.valueOf(i)).start();
        }

        new Thread(()->{
            room.lock();

            try {
                while (!hasFood) {
                    log.debug("没有食物，不干活");
                    food.await();
                    log.debug("获得食物，开始干活");
                }
            } catch (InterruptedException e) {
                log.error("",e);
                log.debug("被赶出房间");
                return;
            } finally {
                room.unlock();
            }
            log.debug("干活完成，退出房间");
        }, "小明").start();

        Thread.sleep(1000);
        new Thread(()->{
            room.lock();
            try {
                log.debug("送来香烟");
                hasCigarette = true;
                cigarette.signalAll();
            } finally {
                room.unlock();
            }
        }).start();
    }
}
