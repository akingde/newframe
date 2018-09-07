package com.newframe.enums.order;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单状态枚举类
 * @author kfm
 * @date 2018年8月16日 11点38分
 */
public enum OrderRenterStatus {
    // type: 1 待处理，2 待审核，3 待收货，4 租机中，5 已完成
    // 待处理
    PENDING(1,1),
    // 订单已取消
    ORDER_CANCEL(2,null),
    // 待资金方审核
    WATIING_FUNDER_AUDIT(3,2),
    // 待出租方审核
    WAITING_LESSOR_AUDIT(4,2),
    // 资金方审核不通过
    FUNDER_AUDIT_REFUSE(5,1),
    // 订单融资已超过三次，不允许再融资
    ORDER_FINANCING_OVER_THREE(21,null),
    // 出租方审核不通过
    LESSOR_AUDIT_REFUSE(6,1),
    //出租方审核不通过超过三次，不允许再租赁
    ORDER_RENT_OVER_THREE(22,null),
    // 资金方线上放款成功,代发货
    FUNDER_ONLINE_LOAN_SUCCESS(7,3),
    // 资金方线下放款成功，代发货
    FUNDER_OFFLINE_LOAN_SUCCESS(8,3),
    // 出租方发货，待收货
    WAITING_LESSOR_RECEIVE(9,3),
    // 供应商发货，待收货
    WAITING_SUPPLIER_RECEIVE(10,3),
    // 已确认收货，变为租赁中
    CONFIRM_RECEIVE_RENTING(11,4),
    // 用户拒收
    CONSUMER_REFUSE_RECEIVE(12,null),
    // 用户坏账/逾期
    CONSUMER_OVERDUE(13,4),
    // 租赁商还款逾期
    RENTER_OVERDUE(14,4),
    // 租赁商坏账
    RENTER_BAD_DEPT(15,4),
    // 租赁商还款完成
    RENTER_FINISH_OVERDUE(16,4),
    // 租机完成，用户还机
    CONSUMER_RETUENING(17,5),
    // 用户续租
    CONSUMER_RELET(18,4),
    // 用户买断
    CONSUMER_BUY_OUT(19,5),
    // 用户还机成功，并校验无误
    CONSUMER_RETURN_COMPLETE(20,5)
    ;
    private Integer type;
    private Integer code;
    private OrderRenterStatus(Integer code,Integer type){
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }public Integer getType() {
        return type;
    }

    public static List<Integer> getStatuses(Integer type){
        OrderRenterStatus[] supplierStatuses = OrderRenterStatus.values();
        List<Integer> statuses = new ArrayList<>();
        for(OrderRenterStatus status:supplierStatuses){
            if(status.getType()!= null && status.getType().equals(type)){
                statuses.add(status.getCode());
            }
        }
        return statuses;
    }
}
