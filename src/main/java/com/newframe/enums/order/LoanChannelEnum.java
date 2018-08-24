package com.newframe.enums.order;

/**
 * @author kfm
 * 资金方放款方式枚举
 */
public enum LoanChannelEnum {
    //线上放款
    ONLINE(1),
    //线下放款
    OFFLINE(2)
    ;
    private Integer value;
    LoanChannelEnum(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
