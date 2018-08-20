package com.newframe.enums.order;

/**
 * 订单状态枚举类
 * @author kfm
 * @date 2018年8月16日 11点38分
 */
public enum OrderLessorStatus {
    // 待出租方审核
    WATIING_LESSOR_AUDIT(1),
    // 出租方审核不通过
    LESSOR_AUDIT_REFUSE(2),
    // 待发货
    WAITING_DELIVER(3),
    // 待收货
    WAITING_RECEIVE(4),
    // 已确认收货，订单进入租赁状态，租机中
    RENTING(5),
    // 续租中
    RELETTING(6),
    // 已买断
    BUY_OUT(7),
    // 还机中
    RETURNING(8),
    // 还机成功
    RETURN_SUCCESS(9),
    // 逾期
    OVER_TIME(10),
    // 坏账
    BED_DEBT(11),
    // 租赁商还款完成
    RENTER_REPAYMENT_COMPLETE(12)
    ;

    private Integer code;
    private OrderLessorStatus(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
