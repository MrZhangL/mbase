package com.zl.communication;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : wait
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-24 10:59
 */
@Slf4j
public class WaitAndNotify {

    private static final Object room = new Object();
    private static boolean hasCigarette = false;
    private static boolean hasFood = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            synchronized (room) {
                while (!hasCigarette) {
                    log.debug("没有香烟，不干活");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        log.error("",e);
                    }
                }
                log.debug("有香烟了，干活完成");
            }
        }, "小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                synchronized (room){
                    log.debug("干活");
                }
            }, String.valueOf(i)).start();
        }

        new Thread(()->{
            synchronized (room) {
                while (!hasFood) {
                    log.debug("没有食物，不干活");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        log.error("",e);
                    }
                }
                log.debug("有食物了，干活完成");
            }
        }, "小明").start();

        Thread.sleep(1000);
        new Thread(()->{
            synchronized (room){
                log.debug("送来香烟");
                hasCigarette = true;
                room.notifyAll();
            }
        }).start();
    }
}
