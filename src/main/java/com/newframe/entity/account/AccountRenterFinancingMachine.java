package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * <p>
 * 租赁商账户表
 * </p>
 *
 * @author wangdong
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountRenterFinancingMachine {
    /**
     * uid
     */
    @Id
    private Long uid;
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
    /**
     * 本月应还
     */
    private BigDecimal monthShouldRepay;

    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;


    public void setAccountRenterFinancingMachine(Long uid,BigDecimal orderFinancing, BigDecimal settleFinancing, BigDecimal unsettledFinancing,BigDecimal monthShouldRepay){
        this.uid = uid;
        this.orderFinancing = orderFinancing;
        this.settleFinancing = settleFinancing;
        this.unsettledFinancing = unsettledFinancing;
        this.monthShouldRepay = monthShouldRepay;
    }
}
