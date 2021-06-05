package com.zl.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class BlockQueue<T> {

    private Deque<T> tasks;
    private ReentrantLock lock;
    private Condition produceCond;
    private Condition consumeCond;
    private int capacity;

    public BlockQueue() {
        tasks = new ArrayDeque<>();
        lock = new ReentrantLock();
        produceCond = lock.newCondition();
        consumeCond = lock.newCondition();
        capacity = 1000;
    }

    public BlockQueue(int capacity) {
        tasks = new ArrayDeque<>();
        lock = new ReentrantLock();
        produceCond = lock.newCondition();
        consumeCond = lock.newCondition();
        this.capacity = capacity;
    }

    public T getTask(){
        T first = null;
        lock.lock();
        try {
            long waitTime = 500;
            long startTime = System.currentTimeMillis();
            while (tasks.isEmpty()) {
                try {
                    long remainTime = waitTime - (System.currentTimeMillis() - startTime);
                    if(remainTime > 0) {
                        produceCond.await(remainTime, TimeUnit.MILLISECONDS);
                    } else {
                        return null; // 等待超时，无法放入，请重试
                    }
                } catch (InterruptedException e) {
                    log.debug("获取任务等待被打断:", e);
                }
            }

            first = tasks.pollFirst();
            consumeCond.signalAll();
        } finally {
            lock.unlock();
        }


        return first;
    }

    public boolean putTask(T task){
        lock.lock();
        try {
            /*long waitTime = 500;
            long startTime = System.currentTimeMillis();*/
            while (tasks.size() >= capacity) {
                try {
                    /*long remainTime = waitTime - (System.currentTimeMillis() - startTime);
                    if(remainTime > 0) {
                        consumeCond.await(remainTime, TimeUnit.MILLISECONDS);
                    } else {
                        return false; // 等待超时，无法放入，请重试
                    }*/
                    consumeCond.await();
                } catch (InterruptedException e) {
                    log.debug("放入阻塞队列等待被打断:", e);
                }
            }

            tasks.push(task);
            produceCond.signalAll();
        } finally {
            lock.unlock();
        }


        return true;
    }
}
