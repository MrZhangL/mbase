package com.zl.communication.protectPause;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : Download
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-24 14:46
 */
@Slf4j
public class Download {

    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(()->{
            try {
                Object o = guardedObject.get(5000);
                log.debug("下载内容: " + o.toString());
            } catch (Exception e) {
                log.error("", e);
            }

        }, "get").start();

        new Thread(()->{
            log.debug("开始下载");

            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                log.error("",e);
            }

            log.debug("下载完成");
            String download = "happy new year!";
            guardedObject.submitObject(download);

        },"download").start();

        
    }


}
