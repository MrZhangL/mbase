package com.zl.Visible;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Volatile {
    final TaskWrapper tsk = new TaskWrapper();

    public static void main(String[] args) throws InterruptedException {
        Volatile vol = new Volatile();
        vol.test();
    }

    private void test() throws InterruptedException {
        Task bk = tsk.task;
        new Thread(()->{
            log.debug("任务开始");
            while (!tsk.isEnding()) {

            }
            log.debug("任务结束");
        }, "t1").start();

        Thread.sleep(1000);

        tsk.end();
        log.debug("change");
    }
}

class TaskWrapper {
    final Task task = new Task();

    public void end() {
        task.ending = true;
    }

    public boolean isEnding() {
        return task.ending;
    }

    /*public void change() {
        task = new Task();
    }*/
}

class Task {
    boolean ending = false;
    String name = "da";

    public boolean isEnding() {
        return ending;
    }
}

