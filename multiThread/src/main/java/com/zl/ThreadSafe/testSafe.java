package com.zl.ThreadSafe;

import java.util.ArrayList;

/**
 * @ClassName : testSafe
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-09 10:01
 */

public class testSafe {
    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;

    public static void main(String[] args) {
        ThreadUnsafe test = new ThreadUnsafe();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method1(LOOP_NUMBER);
            }, "Thread" + i).start();
        }
    }
}

class ThreadUnsafe {
    ArrayList<String> list = new ArrayList<>();
    public void method1(int loopNumber) {
        for (int i = 0; i < loopNumber; i++) {
            // { 临界区, 会产生竞态条件
            method2();
            method3();
            // } 临界区
        }
    }
    private void method2() {
        list.add("1");
        //System.out.println(list.size());
    }
    private void method3() {
        //System.out.println(list.size());
        list.remove(0);
    }
}

