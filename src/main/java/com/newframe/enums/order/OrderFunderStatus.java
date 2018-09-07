package com.newframe.enums.order;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单状态枚举类
 * @author kfm
 * @date 2018年8月16日 11点38分
 */
public enum OrderFunderStatus {
    // type：1 待审核 ，2 待收货 ，3 还款中 ，4 已完成
    // 待审核
    WAITING_AUDIT(1,1),
    // 审核不通过
    AUDIT_REFUSE(2,1),
    // 线上放款成功，待发货
    WAITING_DELIVER(3,2),
    // 线下放款中
    PAYMENTING(4,2),
    // 线下放款，有凭证，待发货
    WAITING_DELIVER_EVIDENCE(5,2),
    // 线下放款，无凭证，待发货
    WAITING_DELIVER_NO_EVIDENCE(6,2),
    // 供应商发货，待收货
    WAITING_RECEIVE(7,2),
    // 还款中
    WAITING_REPAYMENT(8,3),
    // 逾期
    OVERDUE(9,3),
    // 坏账
    BES_DEBT(10,3),
    // 还款完成
    REPAYMENT_COMPLETE(11,4),
    ;
    private Integer type;
    private Integer code;
    private OrderFunderStatus(Integer code,Integer type){
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }public Integer getType() {
        return type;
    }

    public static List<Integer> getStatuses(Integer type){
        OrderFunderStatus[] funderStatuses = OrderFunderStatus.values();
        List<Integer> statuses = new ArrayList<>();
        for(OrderFunderStatus status:funderStatuses){
            if(status.getType()!= null && status.getType().equals(type)){
                statuses.add(status.getCode());
            }
        }
        return statuses;
    }
}
