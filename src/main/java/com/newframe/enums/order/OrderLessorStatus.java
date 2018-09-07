package com.newframe.enums.order;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单状态枚举类
 * @author kfm
 * @date 2018年8月16日 11点38分
 */
public enum OrderLessorStatus {
    // type：1 待审核 ，2 待收货 ，3 租机中 ，4 已完成
    // 待出租方审核
    WATIING_LESSOR_AUDIT(1,1),
    // 出租方审核不通过
    LESSOR_AUDIT_REFUSE(2,1),
    // 待发货
    WAITING_DELIVER(3,2),
    // 待收货
    WAITING_RECEIVE(4,2),
    // 已确认收货，订单进入租赁状态，租机中
    RENTING(5,3),
    // 续租中
    RELETTING(6,3),
    // 已买断
    BUY_OUT(7,4),
    // 还机中
    RETURNING(8,3),
    // 还机成功
    RETURN_SUCCESS(9,4),
    // 逾期
    OVER_TIME(10,3),
    // 坏账
    BED_DEBT(11,3),
    // 租赁商还款完成
    RENTER_REPAYMENT_COMPLETE(12,4)
    ;
    private Integer type;
    private Integer code;
    private OrderLessorStatus(Integer code,Integer type){
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }public Integer getType() {
        return type;
    }

    public static List<Integer> getStatuses(Integer type){
        OrderLessorStatus[] lessorStatuses = OrderLessorStatus.values();
        List<Integer> statuses = new ArrayList<>();
        for(OrderLessorStatus status:lessorStatuses){
            if(status.getType()!= null && status.getType().equals(type)){
                statuses.add(status.getCode());
            }
        }
        return statuses;
    }
}
