package com.zl.fork;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.ReentrantLock;

public class Demo1 {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(6);
        System.out.println(pool.invoke(new MyTask(0, 100, 10)));
        ReentrantLock lock;
    }
}

@Slf4j
class MyTask extends RecursiveTask<Integer> {

    private int start;
    private int end;
    private int interval;

    public MyTask(int start, int end, int interval){
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    @Override
    protected Integer compute() {
        if(end == start){
            return end;
        }

        // 求出分为多少个组，一般组数会等于线程数，自己当前的线程也算一个，因此向上取整再-1
        int divideNum = (int) (Math.ceil((double)(end - start + 1)/interval) - 1);
        int rt = 0;

        // 分派到其他线程完成部分计算
        MyTask[] tasks = new MyTask[divideNum];
        for (int i = 0; i < divideNum; i++) {
            tasks[i] = new MyTask(start + i * interval, start + (i + 1) * interval - 1, interval);
            tasks[i].fork();
        }

        // 计算自己线程的任务
        log.debug("compute {} ~ {}", start + divideNum*interval, end);
        for(int i = start + divideNum*interval; i <= end; i++){
            rt += i;
        }

        // 合并结果
        for(int i = 0; i < divideNum; i++){
            rt += tasks[i].join();
            log.debug("join: {} ~ {}", start + i*interval, start + (i+1)*interval - 1);
        }

        return rt;
    }
}
