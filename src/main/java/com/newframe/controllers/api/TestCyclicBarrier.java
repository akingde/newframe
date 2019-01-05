package com.newframe.controllers.api;

import java.util.concurrent.CyclicBarrier;

/**
 * 测试一下CyclicBarrier
 */
public class TestCyclicBarrier {

    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("操作再次......执行");
            }
        });

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new CyclicBarrierWorker(barrier));
            thread.start();
        }
    }
}
