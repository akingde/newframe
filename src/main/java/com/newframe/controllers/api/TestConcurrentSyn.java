package com.newframe.controllers.api;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;

/**
 * 测试并发包中的集合类
 */
public class TestConcurrentSyn {

    public static void main(String[] args) {

        /**
         * 2。并发包。在工作中，我们更加普遍的是选择利用并发包提供
         * 适合在高度并发的环境下使用
         * 线程安全容器类
         * 这个只要你是按照并发包的标准创建的集合，都是线程安全的。
         */
        //关于map的ConcurrentHashMap
        ConcurrentHashMap<String,Object> map2 = new ConcurrentHashMap<>();
        map2.put("1","我是并发包直接构建的");
        map2.put("2","我是线程安全的Map容器，ConcurrentHashMap");
        map2.forEach((key,value) ->{
            System.out.println("map2:" + key + "," + value);
        });

        //关于list的CopyOnWriteArrayList
        CopyOnWriteArrayList<Integer> list2 = new CopyOnWriteArrayList<>();
        list2.add(67612);
        list2.add(67362);
        list2.forEach(list ->{
            System.out.println(list);
        });

        /**
         * 并发包中的线程安全队列
         */
        //ArrayBlockingQueue
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue(10);
        arrayBlockingQueue.add("1");
        arrayBlockingQueue.add("3");
        arrayBlockingQueue.forEach(queue->{
            System.out.println(queue);
        });
    }
}
