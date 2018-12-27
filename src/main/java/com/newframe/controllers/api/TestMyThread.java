package com.newframe.controllers.api;

public class TestMyThread {

    public static void main(String[] args) {

        //创建MyThread类对象
        MyThread myThread = new MyThread();
        //调用重写过的run方法
        //输出：请去给我找个靠谱的工作
        myThread.run();
    }
}
