package com.zl.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Pool {
    // 阻塞队列
    private BlockQueue<Runnable> waitQueue;

    // 线程池中的线程
    private HashSet<WorkerThread> threads;

    // 线程没有任务后的存活时间，当线程数超过initSize才会杀死线程
    //private Long aliveTime;

    // 时间单位
    //private TimeUnit unit;

    // 线程池的最大容量
    private int maxSize;


    public Pool(int maxSize, int waitSize) {
        this.maxSize = maxSize;

        threads = new HashSet<>();
        waitQueue = new BlockQueue<>(waitSize);
    }

    public void submit(Runnable task){
        synchronized (threads) {
            if (threads.size() < maxSize) {
                log.debug("新增线程");
                WorkerThread thread = new WorkerThread(task);
                thread.start();
                threads.add(thread);
            } else {
                // 线程池已满并且所有线程都繁忙
                // 加入等待队列
                if (!waitQueue.putTask(task)) {
                    log.debug("线程池爆满，放入超时");
                }
                log.debug("任务进入队列");
            }
        }
    }

    private class WorkerThread extends Thread {

        private Runnable task;

        private boolean killed;

        public WorkerThread(Runnable target) {
            //super();  // 不用写，会自动调用默认构造方法
            task  = target;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            while (true) {
                if (task == null) {
                    task = waitQueue.getTask();
                    if (System.currentTimeMillis() - startTime > 5000) {
                        // 判断是否需要杀死该线程
                        threads.remove(this);
                        log.debug("线程被杀死");
                        break;
                    }
                }
                else {
                    try {
                        task.run();
                    } finally {
                        startTime = System.currentTimeMillis();
                        task = null;
                    }
                }
            }
        }

        public void setTask(Runnable task){
            this.task = task;
        }
    }

}

