package com.zl.cas;


import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UnlockList {

    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    public static void test1() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(6);
        ExecutorService executor1 = Executors.newFixedThreadPool(6);

        CountDownLatch downLatch1 = new CountDownLatch(16);
        CountDownLatch downLatch2 = new CountDownLatch(16);

        AtomicInteger cnt = new AtomicInteger();

        UnlockList list = new UnlockList();

        long begin = System.currentTimeMillis();

        for (int i = 0; i < 16; i++) {
            executor.submit(()->{
                for (int j = 0; j < 1000000; j++) {
                    list.add(cnt.getAndIncrement());
                }
                downLatch1.countDown();
            });
        }

        for (int i = 0; i < 16; i++) {
            executor1.submit(()->{
                for (int j = 0; j < 1000000; j++) {
                    Integer val;
                    while((val = list.get()) == null) {
                    }
                    val++;
                }
                downLatch2.countDown();
            });
        }

        downLatch1.await();
        downLatch2.await();

        System.out.println(System.currentTimeMillis() - begin + "=-=======");

        executor.shutdown();
        executor1.shutdown();
    }

    public static void test2() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(6);
        ExecutorService executor1 = Executors.newFixedThreadPool(6);

        CountDownLatch downLatch1 = new CountDownLatch(16);
        CountDownLatch downLatch2 = new CountDownLatch(16);

        AtomicInteger cnt = new AtomicInteger();

        BlockingQueue<Integer> blockingQueue = new LinkedBlockingDeque<>();

        long begin = System.currentTimeMillis();

        for (int i = 0; i < 16; i++) {
            executor.submit(()->{
                for (int j = 0; j < 1000000; j++) {
                    int num = cnt.getAndIncrement();
                    try {
                        blockingQueue.put(num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                downLatch1.countDown();
            });
        }

        for (int i = 0; i < 16; i++) {
            executor1.submit(()->{
                for (int j = 0; j < 1000000; j++) {
                    try {
                        Integer val = null;
                        val = blockingQueue.take();
                        val++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                downLatch2.countDown();
            });
        }

        downLatch1.await();
        downLatch2.await();

        System.out.println(System.currentTimeMillis() - begin + "-----------");

        executor.shutdown();
        executor1.shutdown();
    }

    public static void test3() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(16);
        ExecutorService executor1 = Executors.newFixedThreadPool(16);

        CountDownLatch downLatch1 = new CountDownLatch(16);
        CountDownLatch downLatch2 = new CountDownLatch(16);

        AtomicInteger cnt = new AtomicInteger();

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10000);

        long begin = System.currentTimeMillis();

        for (int i = 0; i < 16; i++) {
            executor.submit(()->{
                for (int j = 0; j < 1000000; j++) {
                    int num = cnt.getAndIncrement();
                    try {
                        queue.put(num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                downLatch1.countDown();
            });
        }

        for (int i = 0; i < 16; i++) {
            executor1.submit(()->{
                for (int j = 0; j < 1000000; j++) {
                    try {
                        Integer val = null;
                        val = queue.take();
                        val++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                downLatch2.countDown();
            });
        }

        downLatch1.await();
        downLatch2.await();

        System.out.println(System.currentTimeMillis() - begin + "-----------");

        executor.shutdown();
        executor1.shutdown();
    }

    private Node head = null;
    private Node tail = null;

    public void add(int val) {
        Node newNode = new Node(val);

        for (;;) {
            Node listTail = tail;
            if(listTail == null) {
                if(compareAndSwapHead(null, newNode)) {
                    tail = head;
                    break;
                }
            }
            else {
                if(compareAndSwapTail(listTail, newNode)) {
                    newNode.pre = listTail;
                    listTail.next = newNode;
                    break;
                }
            }

            Thread.yield();     // 让出cpu
        }
    }

    public Integer get() {
        for (;;) {
            Node hd = head;
            if(hd == null) {
                return null;
            }

            Node nxt = hd.next;
            if(nxt == null) {
                // 只有一个，弹出后需要置为null
                if(compareAndSwapTail(hd, null)) {
                    head = null;
                    return hd.val;
                }
            } else {
              if(compareAndSwapHead(hd, nxt)) {
                  nxt.pre = null;
                  hd.next = null;
                  return hd.val;
              }
            }

            Thread.yield();     // 让出cpu
        }


    }

    private boolean compareAndSwapTail(Node expect, Node target) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, target);
    }

    private boolean compareAndSwapHead(Node expect, Node target) {
        return unsafe.compareAndSwapObject(this, headOffset, expect, target);
    }

    static class Node {
        Integer val;
        volatile Node next;
        volatile Node pre;

        Node(int val) {
            this.val = val;
        }
    }
    
    private static Unsafe unsafe;
    private static long tailOffset;
    private static long headOffset;

    static {
        try {
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);
            tailOffset = unsafe.objectFieldOffset(UnlockList.class.getDeclaredField("tail"));
            headOffset = unsafe.objectFieldOffset(UnlockList.class.getDeclaredField("head"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
