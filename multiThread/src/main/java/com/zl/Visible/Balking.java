package com.zl.Visible;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : Balking
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-05-01 11:31
 */

public class Balking {

    public static void main(String[] args) throws InterruptedException {
        MonitorService monitorService = new MonitorService();
        new Thread(()->{monitorService.start();}).start();
        new Thread(monitorService::start).start();
        new Thread(()->{monitorService.start();}).start();

        while(true){
            Thread.sleep(200);
        }
    }
}

@Slf4j
class MonitorService {

    private volatile boolean started= false;

    public MonitorService(){
        started = false;
    }

    public void start() {
        synchronized (this){
            if(started){
               return;
            }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        started = true;
        }

        new Thread(()->{
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("监控启动");

            while(started){
                log.debug("监控中");
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stop(){

        started = false;

        log.debug("监控关闭");
    }
}
