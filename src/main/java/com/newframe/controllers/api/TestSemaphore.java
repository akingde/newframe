package com.newframe.controllers.api;

import java.util.concurrent.Semaphore;

/**
 * 第二步
 * 创建一个Semaphore的测试类
 */
public class TestSemaphore {

    public static void main(String[] args) {

        System.out.println("我要开始执行了");

        //创建一个Semaphore对象，允许3个资源
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            SemaphoreWorker worker = new SemaphoreWorker(semaphore);
            //给线程一个名字
            worker.setName("线程"+i);
            Thread thread = new Thread(worker);
            thread.start();
        }
    }
}
