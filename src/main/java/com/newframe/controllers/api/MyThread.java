package com.newframe.controllers.api;

/**
 * 创建一个MyThread类来继承Thread类
 */
public class MyThread extends Thread{

    //在使用这个MyThread实现类的时候，首先要先重写他的run方法，让他去做你要求的事

    @Override
    public void run() {
        //例如
        System.out.println("请去给我找个靠谱的工作");
        //下面这一句可以不要
        // super.run();
    }
}
