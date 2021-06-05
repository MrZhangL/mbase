package com.tomcode.agent.jvm;

import java.util.Arrays;

public class Classld {
    public static void main(String[] args) throws ClassNotFoundException {

        Classld classld = new Classld();
        try {
            classld.f(10);
        } catch (Throwable t) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            Arrays.stream(stackTrace).forEach(stackTraceElement -> System.out.println(stackTraceElement.getClassName() +
                    "." + stackTraceElement.getMethodName()));
            t.printStackTrace();
        }
    }

    public void f(int x) {
        if(x == -1) return;
        int a = 1/x;
        f(x-1);
    }
}

