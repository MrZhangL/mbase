package com.zl.mypool;

import java.util.concurrent.TimeUnit;

public interface BlocQueue<E> {

    // out
    // 一直阻塞
    E take() throws InterruptedException;

    // 不阻塞，没有元素返回null
    E poll();

    // 带超时阻塞
    E poll(long time, TimeUnit unit) throws InterruptedException;


    // enter
    // 一直阻塞
    void put(E e) throws InterruptedException;

    // 不阻塞
    boolean offer(E e);

    // 带超时的阻塞
    boolean offer(long time, TimeUnit unit) throws InterruptedException;

    // 返回元素个数
    int size();

    boolean isEmpty();

}
