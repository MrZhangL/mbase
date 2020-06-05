package com.zl.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class PoolHungry {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);



        service.execute(()->{
            log.debug("点餐, 去找人做菜");
            Future<String> future = service.submit(() -> {
                log.debug("做菜");
                return "宫保鸡丁";
            });

            try {
                log.debug("上菜: " + future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        service.execute(()->{
            log.debug("点餐, 去找人做菜");
            Future<String> future = service.submit(() -> {
                log.debug("做菜");
                return "辣子鸡丁";
            });

            try {
                log.debug("上菜: " + future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
