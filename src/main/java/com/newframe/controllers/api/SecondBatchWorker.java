package com.newframe.controllers.api;

import java.util.concurrent.CountDownLatch;

/**
 * 创建第二个吃巧克力的家伙，叫他小李吧
 * 实现Runnable接口，并重写其方法
 */
public class SecondBatchWorker implements Runnable{

    private CountDownLatch latch;

    public SecondBatchWorker(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {

        try {
            latch.await();
            System.out.println("小李吃了一块巧克力");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
