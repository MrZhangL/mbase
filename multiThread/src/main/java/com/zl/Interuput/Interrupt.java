package com.zl.Interuput;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class Interrupt {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("阻塞...");
                LockSupport.park();
                log.debug("打断");
            }
        });

        t1.start();
        Thread.sleep(100);
        LockSupport.unpark(t1);

    }
}
