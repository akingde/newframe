package com.newframe.controllers.api;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;

/**
 * 测试传统线程安全的集合类
 */
public class TestTraditionSyn {

    public static void main(String[] args) {

        /**
         * 1。在传统的集合框架中。
         * 除了Hashtable这个是线程安全的同步容器。
         * 他的实现基本上就是将Put、get、size等各种
         * 方法的操作加上"synchronized"。这就导致了所有的并发操作都在竞争同一把锁
         * 一个线程在进行同步操作时，其他线程只能等待，大大降低了并发执行的效率
         * （当然这个因为同步的线程开销较大，不推荐使用）
         * 还可以通过调用Collections工具类提供的包装类。来构造线程安全的同步包装容器，如下所示
         */
        //构造一个线程安全的List
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        list.add("hello");
        list.add("world");

        Iterator iterator1 = list.iterator();
        while (iterator1.hasNext()){
            System.out.println(iterator1.next());
        }

        //构造一个线程安全的Map
        Map<String,Object> map1 = Collections.synchronizedMap(new HashMap<>());
        map1.put("1","wang");
        map1.put("2","dong");

        map1.forEach((key,value) ->{
            System.out.println("map1:" + key + "," + value);
        });
    }
}
