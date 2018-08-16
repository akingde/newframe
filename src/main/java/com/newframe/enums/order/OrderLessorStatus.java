package com.newframe.enums.order;

/**
 * 订单状态枚举类
 * @author kfm
 * @date 2018年8月16日 11点38分
 */
public enum OrderLessorStatus {
    // 待处理
    PENDING(1),
    // 待资金方审核
    WATIING_FUNDER_AUDIT(2),
    // 待出租方审核
    WAITING_LESSOR_AUDIT(3),
    // 资金方审核不通过
    FUNDER_AUDIT_REFUSE(4),
    // 出租方审核不通过
    LESSOR_AUDIT_REFUSE(5),
    // 待发货
    WAITING_DELIVER(6),
    // 待收货
    WAITING_RECEIVE(7),
    // 已确认收货
    CONFIRM_RECEIPT(8),
    // 租赁商已取消订单
    RENTER_CANCEL_ORDER(9),
    //
    ;

    private Integer code;
    private OrderLessorStatus(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
