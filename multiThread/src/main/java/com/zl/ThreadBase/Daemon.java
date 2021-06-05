package com.zl.ThreadBase;

/**
 * @ClassName : Daemon
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-06 15:31
 */

public class Daemon {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                int  a= 1;
               /*if(Thread.currentThread().isInterrupted()){
                   break;
               }*/
            }
        }, "t1");

        t1.setDaemon(true);
        t1.start();



        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("main线程结束");
    }
}
