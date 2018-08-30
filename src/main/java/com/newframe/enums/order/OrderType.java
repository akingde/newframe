package com.newframe.enums.order;

/**
 * @author kfm
 * @date 2018.08.30 20:44
 */

public enum OrderType {
    // 出租方订单
    LESSOR_ORDER(1),
    // 资金方订单
    FUNDER_ORDER(2),
    ;
    private Integer code;

    public Integer getCode() {
        return code;
    }
    OrderType(Integer code){
        this.code = code;
    }
}
