package com.zl.communication.protectPause;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : GuardedObject
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-24 14:37
 */
@Slf4j
public class GuardedObject {

    private Object response;

    public synchronized Object get(long timeout) throws Exception {
        long startTime = System.currentTimeMillis();
        while (response == null){
            try {
                long waitTime = System.currentTimeMillis() - startTime;
                long delay = timeout - waitTime;
                if(delay > 0) {
                    this.wait(delay);
                } else {
                    break;
                }
            } catch (InterruptedException e) {
                log.error("wait error:", e);
            }
        }

        if(response == null){
            log.error("下载超时");
            throw new Exception("下载超时");
        }

        return response;
    }

    public synchronized void submitObject(Object response){
        this.response = response;
        this.notifyAll();
    }

}
