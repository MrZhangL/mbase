package com.zl.ThreadBase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName : CreateThread_FuctureTask
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-04 17:47
 */

public class CreateThread_FutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "执行了");
                return 1;
            }
        });

        Thread thread = new Thread(task, "thread1");
        thread.start();

        Integer integer = task.get();   // 阻塞，等待task任务的返回结果
        System.out.println(integer);    // 打印1
    }

    public static void method1(int x){
        int y = x + 1;
        Object o = method2();
        System.out.println(y);
    }

    private static Object method2() {
        Object o = new String();
        return o;
    }
}
