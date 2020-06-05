package com.zl.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class UseInteger {
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(1000);
        for(int i = 0; i < 100; i++) {
            new Thread(() -> {
                account.withdraw(10);
            }).start();
        }

        Thread.sleep(2000);
        System.out.println(account.getBalance());
    }


}

class Account {
    private AtomicInteger balance;

    public Account(int balance){
        this.balance = new AtomicInteger(balance);
    }

    public void withdraw(int num) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true){
            int pre = balance.get();
            int target = pre - num;
            if(balance.compareAndSet(pre, target)){
                break;
            }
        }
    }

    public int getBalance(){
        return balance.get();
    }
}