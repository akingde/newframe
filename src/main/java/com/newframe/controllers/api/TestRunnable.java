package com.newframe.controllers.api;

public class TestRunnable {

    public static void main(String[] args) {

        /**
         * 在Java8之前，这是唯一一种创建Runnable的方式
         */
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("这是Java8以前的创建runnable的方式");
            }
        };

        /**
         * 在Java8引入了lambda表达式后的方式
         */
        Runnable r2 = () -> System.out.println("这是Java8引入了lambda表达式后创建的runnable的方式");

        /**
         * 在这边，你这样调用.run()即可运行，当然，我们一搬不这样做。
         */
        r1.run();
        r2.run();

        /**
         * 我们一搬都是将Runnable对象传递到Thread类接收Runnable作为参数的构造函数中
         * 然后启动该线程
         */
        //下面对这个线程进行一些操作
        /**
         * 1.设置一个名字,通过构造的方式
         */
        Thread t1 = new Thread(r1,"小明线程");
        t1.start();
        System.out.println(t1.getName());//Output 小明线程

        Thread t2 = new Thread(r2);
        /**
         * 2.查看线程的存活状态
         * 一个线程的寿命在他被start()方法启动了起来
         */
        //下面这个很显然是死的，false
        System.out.println(t2.isAlive());
        t2.start();
        t2.setName("小李线程");
        System.out.println(t2.getName()); //Output 小李线程
        //这个在start方法后，很显然是true，活得
        System.out.println(t2.isAlive());

        /**
         * 获取一条线程的执行状态
         * NEW 新建的线程，还没有运行
         * RUNNABLE
         * BLOCKED
         * WAITING
         * TIMED_WAITING
         * TERMINATED
         */
        Thread t3 = new Thread(r2);
        System.out.println(t2.getState());
        t3.start();
    }
}
