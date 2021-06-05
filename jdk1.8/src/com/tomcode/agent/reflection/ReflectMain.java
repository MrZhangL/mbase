package com.tomcode.agent.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectMain {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Father f = new Father();
        Method method = f.getClass().getMethod("print");
        method.invoke(f);

        System.out.println(method.getDeclaringClass());
    }
}

class Father {
    public void print() {
        System.out.println("father");
    }

    public void print(String s) {
        System.out.println("father");
    }

    public void prints1() {
        System.out.println("father");
    }

    public void prints2() {
        System.out.println("father");
    }
}

class Son extends Father {
    public void pp() {
        System.out.println("pp");
    }
}
