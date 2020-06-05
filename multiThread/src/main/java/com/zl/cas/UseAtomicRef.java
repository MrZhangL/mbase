package com.zl.cas;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class UseAtomicRef {
    public static void main(String[] args) throws InterruptedException {
        AccountRef account = new AccountRef("1000");
        for(int i = 0; i < 100; i++) {
            new Thread(() -> {
                account.withdraw(10D);
            }).start();
        }

        Thread.sleep(2000);
        System.out.println(account.getBalance());
    }


}

class AccountRef{
    private AtomicReference<BigDecimal> balance;

    public AccountRef(String balance) {
        this.balance = new AtomicReference<>(new BigDecimal(balance));
    }

    public void withdraw(Double num){
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true){
            BigDecimal pre = balance.get();
            BigDecimal target = pre.subtract(new BigDecimal(num.toString()));
            if(balance.compareAndSet(pre, target)){
                break;
            }
        }
    }

    public BigDecimal getBalance(){
        return balance.get();
    }
}
