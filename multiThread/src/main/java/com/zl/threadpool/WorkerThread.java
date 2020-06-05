package com.zl.threadpool;

public class WorkerThread extends Thread {

    public WorkerThread(Runnable target) {
        super(target);
    }


}
