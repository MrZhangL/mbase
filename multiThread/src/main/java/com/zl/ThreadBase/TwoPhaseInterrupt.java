package com.zl.ThreadBase;

/**
 * @ClassName : TwoPhaseInterrupt
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-06 14:52
 */

public class TwoPhaseInterrupt {


    public static void main(String[] args){
        // 启动线程
        Thread t1 = new Thread(() -> {
            while (true) {
                Thread thread = Thread.currentThread();
                if (thread.isInterrupted()) {
                    // 被打断
                    System.out.println("料理后事");
                    break;
                }
                try {
                    Thread.sleep(200);
                    System.out.println("处理监控");
                } catch (InterruptedException e) {
                    thread.interrupt();     // 异常进入会清空打断标记，需要打断一次
                    e.printStackTrace();
                }
            }
        }, "t1");

        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();
    }
}
