package com.zl.ThreadBase;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : StartAndRun
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-04 22:35
 */
@Slf4j
public class StartAndRun {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                System.out.println(currentThread().getName() + ": hello, t1!");
            }
        };
        log.info((t1.getState().toString()));
        t1.start();
        System.out.println(t1.getState());
        System.out.println(Thread.currentThread().getName() + ": hello,main!");
    }
}
