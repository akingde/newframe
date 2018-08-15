package com.newframe.enums.order;

import lombok.Data;

/**
 * 订单按时间排序顺序
 * @author kfm
 */
public enum OrderSort {
    /**1：按时间从近到远*/
    DESC(1),
    /**2：按时间从远到近*/
    ASC(2);
    private Integer value;
    private OrderSort(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
