package com.newframe.controllers.api;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author:wangdong
 * @description:
 */
@Data
public class User {

    private Long id;

    private BigDecimal money;

    public User(Long id,BigDecimal money){
        this.id=id;
        this.money=money;
    }
}
