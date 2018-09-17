package com.newframe.utils;

/**
 * Created by tt on 7/30/18.
 */
public class MathUtils {

    /**
     * 生成随机数
     *
     * @param length
     * @return
     */
    public static int getRandom(int length) {
        int pow = (int) Math.pow(10, length - 1);
        return (int) ((Math.random() * 9 + 1) * pow);
    }

    /**
     * 生成业务流水(时间+随机数)
     *
     * @return
     */
    public static String getNo(int length) {
        int pow = (int) Math.pow(10, length);
        return String.valueOf(System.currentTimeMillis() * pow + MathUtils.getRandom(length));
    }

    /**
     * 生成业务流水(时间+3位随机数)
     *
     * @return
     */
    public static String getNo() {
        return getNo(3);
    }


    public static void main(String[] args) {
        System.out.println(getRandom(4));
        System.out.println(System.currentTimeMillis());
        System.out.println(getNo());
    }
}
