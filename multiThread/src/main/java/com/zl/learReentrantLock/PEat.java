package com.zl.learReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName : PEat
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-30 10:49
 */

public class PEat {
    public static void main(String[] args) {
        Chopstick c1 = new Chopstick(1);
        Chopstick c2 = new Chopstick(2);
        Chopstick c3 = new Chopstick(3);
        Chopstick c4 = new Chopstick(4);
        Chopstick c5 = new Chopstick(5);

        new Philosopher("柏拉图", c1, c2).start();
        new Philosopher("亚里士多德", c2, c3).start();
        new Philosopher("阿基米德", c3, c4).start();
        new Philosopher("苏格拉底", c4, c5).start();
        new Philosopher("赫拉克利特", c5, c1).start();
    }

}

class Chopstick extends ReentrantLock {
    private int id;

    public Chopstick(int id) {
        this.id = id;
    }
}

@Slf4j
class Philosopher extends Thread{

    String pname;
    Chopstick left;
    Chopstick right;


    public Philosopher(String pname, Chopstick left, Chopstick right) {
        super(pname);
        this.pname = pname;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            if(left.tryLock()){
                try {
                    if(right.tryLock()){
                        try {
                            log.debug("eating....");
                            Thread.sleep(100);
                        } catch (Exception e) {
                            log.error("",e);
                        } finally {
                            right.unlock();
                        }
                    }
                } finally {
                    left.unlock();
                }
            }
        }
    }
}