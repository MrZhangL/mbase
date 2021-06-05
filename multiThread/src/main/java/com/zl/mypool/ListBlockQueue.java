package com.zl.mypool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ListBlockQueue<E> implements BlocQueue<E> {

    // 队列的最大数量
    private volatile int maxSize;

    private final ReentrantLock putLock = new ReentrantLock();  // 队头的锁
    private final ReentrantLock takeLock = new ReentrantLock(); // 队尾的锁
    private final Condition putCond = putLock.newCondition();
    private final Condition takeCond = takeLock.newCondition();

    private volatile ListNode head;
    private volatile ListNode tail;

    // 当前队列中的元素个数
    private volatile int size;

    class ListNode {
        // 元素
        E element;

        ListNode pre;

        ListNode next;

        ListNode(E e) {
            this.element = e;
        }
    }



    public ListBlockQueue(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
        this.head = this.tail = null;
    }

    @Override
    public E take() throws InterruptedException {
        E e = null;
        try {
            takeLock.lock();
            if(size == 0) {
                takeCond.await();
            }
            ListNode p = tail;
            if(p == null) throw new RuntimeException("await error!");

            e = p.element;
            p = p.pre;
            p.next = null;
            tail = p;   // 是否需要用cas？
        } finally {
            takeLock.unlock();
        }

        return e;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E poll(long time, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public void put(E e) throws InterruptedException {

    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public boolean offer(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }




}
