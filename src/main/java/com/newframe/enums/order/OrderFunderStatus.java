package com.newframe.enums.order;

/**
 * 订单状态枚举类
 * @author kfm
 * @date 2018年8月16日 11点38分
 */
public enum OrderFunderStatus {
    // 待审核
    WAITING_AUDIT(1),
    // 审核不通过
    AUDIT_REFUSE(2),
    // 线上放款成功，待发货
    WAITING_DELIVER(3),
    // 线下放款中
    PAYMENTING(4),
    // 线下放款，有凭证，待发货
    WAITING_DELIVER_EVIDENCE(5),
    // 线下放款，无凭证，待发货
    WAITING_DELIVER_NO_EVIDENCE(6),
    // 供应商发货，待收货
    WAITING_RECEIVE(7),
    // 还款中
    WAITING_REPAYMENT(8),
    // 逾期
    OVERDUE(9),
    // 坏账
    BES_DEBT(10),
    // 还款完成
    REPAYMENT_COMPLETE(11),
    ;

    private Integer code;
    private OrderFunderStatus(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
