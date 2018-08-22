package com.newframe.enums.order;

/**
 * 订单状态枚举类
 * @author kfm
 * @date 2018年8月16日 11点38分
 */
public enum OrderRenterStatus {
    // 待处理
    PENDING(1),
    // 订单已取消
    ORDER_CANCEL(2),
    // 待资金方审核
    WATIING_FUNDER_AUDIT(3),
    // 待出租方审核
    WAITING_LESSOR_AUDIT(4),
    // 资金方审核不通过
    FUNDER_AUDIT_REFUSE(5),
    // 订单融资已超过三次，不允许再融资
    ORDER_FINANCING_OVER_THREE(21),
    // 出租方审核不通过
    LESSOR_AUDIT_REFUSE(6),
    // 资金方线上放款成功,代发货
    FUNDER_ONLINE_LOAN_SUCCESS(7),
    // 资金方线下放款成功，代发货
    FUNDER_OFFLINE_LOAN_SUCCESS(8),
    // 出租方发货，待收货
    WAITING_LESSOR_RECEIVE(9),
    // 供应商发货，待收货
    WAITING_SUPPLIER_RECEIVE(10),
    // 已确认收货，变为租赁中
    CONFIRM_RECEIVE_RENTING(11),
    // 用户拒收
    CONSUMER_REFUSE_RECEIVE(12),
    // 用户坏账/逾期
    CONSUMER_OVERDUE(13),
    // 租赁商还款逾期
    RENTER_OVERDUE(14),
    // 租赁商坏账
    RENTER_BAD_DEPT(15),
    // 租赁商还款完成
    RENTER_FINISH_OVERDUE(16),
    // 租机完成，用户还机
    CONSUMER_RETUENING(17),
    // 用户续租
    CONSUMER_RELET(18),
    // 用户买断
    CONSUMER_BUY_OUT(19),
    // 用户还机成功，并校验无误
    CONSUMER_RETURN_COMPLETE(20)
    ;

    private Integer code;
    private OrderRenterStatus(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
