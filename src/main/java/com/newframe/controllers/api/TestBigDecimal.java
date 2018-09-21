package com.newframe.controllers.api;

import java.math.BigDecimal;

/**
 * @author:wangdong
 * @description:
 */
public class TestBigDecimal {

    public static void main(String[] args) {

        BigDecimal a = new BigDecimal(100.55);
        BigDecimal b = new BigDecimal(110.66);
        System.out.println(a.compareTo(b));
        System.out.println(b.compareTo(a));

        boolean result = a.compareTo(b) == -1;
        System.out.println(result);
    }
}
