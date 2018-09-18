package com.newframe.order;

import java.util.Random;

/**
 * @author kfm
 * @date 2018.09.18 14:51
 */
public class Test {

    @org.junit.Test
    public void test(){
        Random random = new Random();
        for (int i=0;i<100;i++)
        System.out.println(random.nextInt(3));
    }
}
