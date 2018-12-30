package com.newframe.controllers.api;

import java.util.concurrent.CountDownLatch;

/**
 * 创建第一个吃巧克力的家伙，叫他小明吧
 * 实现Runnable接口，并重写其方法
 */
public class FirstBatchWorker implements Runnable{

    private CountDownLatch latch;

    public FirstBatchWorker(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        //执行countDown()操作
        latch.countDown();
        System.out.println("小明吃了一块巧克力");
    }
}
