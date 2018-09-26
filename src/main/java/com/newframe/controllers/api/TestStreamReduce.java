package com.newframe.controllers.api;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
public class TestStreamReduce {

    public static void main(String[] args) {

        List<User> users = Lists.newArrayList();

        BigDecimal result1 = BigDecimal.ZERO;
        for (Long i = 0L; i < 10; i++){
            User user = new User(i,new BigDecimal(83.23));
            users.add(user);
        }

        BigDecimal result2 = users.stream().map(User::getMoney).reduce(BigDecimal.ZERO,BigDecimal::add);
        System.out.println(result2);
    }
}
