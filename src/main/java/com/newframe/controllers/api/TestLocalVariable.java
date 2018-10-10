package com.newframe.controllers.api;

/**
 * @author:wangdong
 * @description:
 */
public class TestLocalVariable {

    public static void main(String[] args) {
        {
            //像里面填充64MB的数据
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
    }
}
