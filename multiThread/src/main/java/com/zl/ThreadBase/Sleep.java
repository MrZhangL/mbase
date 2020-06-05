package com.zl.ThreadBase;

/**
 * @ClassName : Sleep
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-04 22:53
 */

public class Sleep {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("t1, weak up");
                    e.printStackTrace();
                }
                System.out.println(currentThread().getName() + ": hello, t1!");
            }
        };

        t1.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();

        System.out.println(Thread.currentThread().getName() + ": hello,main!");


    }
}
