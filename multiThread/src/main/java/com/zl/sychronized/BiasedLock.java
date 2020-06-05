package com.zl.sychronized;

import org.openjdk.jol.info.ClassLayout;

/**
 * @ClassName : BiasedLock
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-13 13:59
 */

public class BiasedLock {

    public static void main(String[] args) throws InterruptedException {
        Dog dog = new Dog();
        System.out.println(ClassLayout.parseInstance(dog).toPrintable());

        synchronized (dog){
            Thread.sleep(10);
            System.out.println(ClassLayout.parseInstance(dog).toPrintable());
        }

        System.out.println(ClassLayout.parseInstance(dog).toPrintable());
    }
}


class Dog{
}
