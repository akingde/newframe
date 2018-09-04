package com.newframe.controllers;

import java.lang.reflect.Method;

/**
 * @author:wangdong
 * @description:
 */
public class TestReflect {
    public static void target(int i){
        new Exception("#" + i).printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        Class<?> kclass = Class.forName("TestReflect");
        Method method = kclass.getMethod("target",int.class);
        method.invoke(null,0);
    }
}
