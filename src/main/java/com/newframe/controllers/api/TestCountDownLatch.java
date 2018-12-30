package com.newframe.controllers.api;

import java.util.concurrent.CountDownLatch;

/**
 * 来测试一下LatchSample
 */
public class TestCountDownLatch {

    public static void main(String[] args) {

        //一共5个巧克力
        //同时有5个线程可以获取到资源
        CountDownLatch latch = new CountDownLatch(5);

        //五个小明线程
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new FirstBatchWorker(latch));
            thread.start();
        }

        //这个线程一定要等到上个线程全部执行完成了才可以执行
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new SecondBatchWorker(latch));
            thread.start();
        }
    }
}
