package com.newframe.enums.order;

import com.newframe.entity.order.OrderSupplier;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单状态枚举类
 * @author kfm
 * @date 2018年8月16日 11点38分
 */
public enum OrderSupplierStatus {
    // type: 1 代发货，2 待收货，3 已完成
    // 收到发货订单，待付款
    WAITING_PAYMENT(1,null),
    // 资金方线下付款中，下单成功还未确认已放款，放款中
    PAYMENTING(2,null),
    // 资金方付款完成，变为待发货
    WAITING_DELIVER(3,1),
    // 供应商发货，待收货
    WAITING_RECEIVE(4,2),
    // 用户已签收
    CONSUMER_CONFIRM_RECEIVE(5,3),
    ;

    private Integer code;
    private Integer type;
    private OrderSupplierStatus(Integer code,Integer type){
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getType() {
        return type;
    }

    public static List<Integer> getStatuses(Integer type){
        OrderSupplierStatus[] supplierStatuses = OrderSupplierStatus.values();
        List<Integer> statuses = new ArrayList<>();
        for(OrderSupplierStatus status:supplierStatuses){
            if(status.getType()!= null && status.getType().equals(type)){
                statuses.add(status.getCode());
            }
        }
        return statuses;
    }
}
