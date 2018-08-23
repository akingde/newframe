package com.newframe.enums.order;

/**
 * 订单状态枚举类
 * @author kfm
 * @date 2018年8月16日 11点38分
 */
public enum OrderSupplierStatus {
    // 收到发货订单，待付款
    WAITING_PAYMENT(1),
    // 资金方线下付款中，下单成功还未确认已放款，放款中
    PAYMENTING(2),
    // 资金方付款完成，变为待发货
    WAITING_DELIVER(3),
    // 供应商发货，待收货
    WAITING_RECEIVE(4),
    // 用户已签收
    CONSUMER_CONFIRM_RECEIVE(5),
    ;

    private Integer code;
    private OrderSupplierStatus(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
