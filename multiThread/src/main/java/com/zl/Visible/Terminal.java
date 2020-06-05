package com.zl.Visible;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : Terminal
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-05-01 10:48
 */
@Slf4j
public class Terminal {

    private static volatile boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            while(true){
                if(!run){
                    log.debug("料理后事");
                    break;
                }

                // 没有结束，完成该有的工作
                log.debug("工作");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();


        Thread.sleep(2000);
        run = false;      // 打断t1线程
        t1.join();
    }
}
