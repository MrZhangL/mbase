package com.zl.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class MyLock implements Lock {
    static int nums = 100;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lk = new ReentrantLock();
        lk.lockInterruptibly();
        Condition condition = lk.newCondition();
        condition.await();
        MyLock lock = new MyLock();

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                lock.lock();
                int no = nums;
                log.debug("买到第{}张票", nums);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                nums--;
                lock.unlock();
            }).start();
        }
        
        Thread.sleep(2000);
        System.out.println(nums);
    }

    private MySyn syn = new MySyn();

    // 上锁，阻塞
    @Override
    public void lock() {
        syn.acquire(1); // 该方法会调用tryAcquire方法，如果不成功就放入等待队列
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        syn.acquireInterruptibly(1);    // 该方法会调用tryAcquire方法，如果不成功就放入等待队列
    }

    @Override
    public boolean tryLock() {
        return syn.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return syn.tryAcquireNanos(1, unit.toNanos(time));  // 该方法会调用tryAcquire方法
    }

    @Override
    public void unlock() {
        syn.release(1); // 该方法会调用tryRelease方法，然后唤醒正在阻塞的线程
    }

    @Override
    public Condition newCondition() {
        return syn.newCondition();
    }


    // 自定义同步器
    class MySyn extends AbstractQueuedSynchronizer {

        // 尝试获得锁
        @Override
        protected boolean tryAcquire(int acquires) {
            if(acquires == 1){
                if(compareAndSetState(0, 1)){   // 如果state先前为0就能够设置为1
                    setExclusiveOwnerThread(Thread.currentThread());    // 设置当前线程独占
                    return true;
                }
            }

            // 锁已经被占，不能获取锁
            return false;
        }

        // 尝试释放锁
        @Override
        protected boolean tryRelease(int arg) {
            if(arg == 1){
                if(getState() != 1){
                    throw new IllegalMonitorStateException();   // 没有被锁释放锁出错
                }
                setExclusiveOwnerThread(null);  // 解除锁的拥有者
                // 释放锁，不需要CAS，其他线程无法改变，state变量为volatile修饰，写在后面可以将线程拥有者也刷新到主存
                setState(0);

                return true;
            }

            return false;
        }

        // 判断锁是否被占用状态
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition(){
            return new ConditionObject();   // 锁变量
        }
    }
}
