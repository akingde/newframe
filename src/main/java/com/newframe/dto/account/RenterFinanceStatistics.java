package com.newframe.dto.account;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author:wangdong
 * @description:融资订单列表的统计
 */
@Data
public class RenterFinanceStatistics {

    /**
     * 订单融资金额
     */
    private BigDecimal orderFinancing;
    /**
     * 已结清融资本息
     */
    private BigDecimal settleFinancing;
    /**
     * 未结清融资本息
     */
    private BigDecimal unsettledFinancing;

    public void setRenterFinanceStatistics(BigDecimal orderFinancing,BigDecimal settleFinancing,BigDecimal unsettledFinancing){
        this.orderFinancing = orderFinancing;
        this.settleFinancing =settleFinancing;
        this.unsettledFinancing = unsettledFinancing;
    }
}
