package com.newframe.controllers.api;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Semaphore;

/**
 * 第一步
 * 创建一个实现了Runnable接口的信号量工作类
 * 并重写它的run方法，让他做我想要做的事情
 */

public class SemaphoreWorker implements Runnable{

    private String name;

    private Semaphore semaphore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //创建一个构造函数
    public SemaphoreWorker(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {

        try {
            log("准备申请一个资源");
            semaphore.acquire();//申请资源
            log("申请到了资源");
            log("执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            semaphore.release();
            log("释放资源成功");
        }

    }

    private void log(String msg){
        if (StringUtils.isEmpty(msg)){
            name = Thread.currentThread().getName();
        }

        System.out.println(name + " " + msg);
    }
}
