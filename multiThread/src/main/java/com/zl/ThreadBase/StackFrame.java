package com.zl.ThreadBase;

/**
 * @ClassName : StackFrame
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-04 20:25
 */

public class StackFrame {

    public static void main(String[] args) {
        new Thread(()->{
            method1(10);
        }, "t1").start();

        method1(20);
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
