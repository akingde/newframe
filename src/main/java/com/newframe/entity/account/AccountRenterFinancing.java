package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * <p>
 * </p>
 *
 * @author wangdong
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountRenterFinancing {
    /**
     * id
     */
    @Id
    private Long id;

    /**
     * uid
     */
    private Long uid;
    /**
     * 平台自己的订单ID
     */
    private Long orderId;
    /**
     * 关联订单的ID
     */
    private String associatedOrderId;
    /**
     * 关联订单的状态
     */
    private String associatedOrderStatus;
    /**
     * 融资金额
     */
    private BigDecimal financingAmount;
    /**
     * 融资期限
     */
    private Integer financingMaturity;

    /**
     * 融资本金
     */
    private BigDecimal financingPrincipalInterest;

    /**
     * 融资利息
     */
    private BigDecimal financingInterest;

    /**
     * 已经偿还的本金
     */
    private BigDecimal settlePrincipalInterest;

    /**
     * 已经偿还的利息
     */
    private BigDecimal settleInterest;

    /**
     * 待偿还的本金
     */
    private BigDecimal unsettlePrincipalInterest;

    /**
     * 待偿还的利息
     */
    private BigDecimal unsettleInterest;

    /**
     * 实际上页面用的是这个
     * 还款状态，1:正常，2:逾期，3:逾期已经结清
     */
    private Integer repaymentStatus;

    /**
     * 订单的状态。1:正常，删除
     */
    private Integer orderStatus;

    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;

    public void setAccountRenterFinancing(Long uid, Long orderId, String associatedOrderId, BigDecimal financingAmount, Integer financingMaturity,
                                          BigDecimal financingPrincipalInterest, BigDecimal financingInterest, BigDecimal settlePrincipalInterest, BigDecimal settleInterest, BigDecimal unsettlePrincipalInterest, BigDecimal unsettleInterest){
        this.uid =uid;
        this.orderId = orderId;
        this.associatedOrderId =associatedOrderId;
        this.financingAmount = financingAmount;
        this.financingMaturity = financingMaturity;
        this.financingPrincipalInterest = financingPrincipalInterest;
        this.financingInterest = financingInterest;
        this.settlePrincipalInterest = settlePrincipalInterest;
        this.settleInterest = settleInterest;
        this.unsettlePrincipalInterest = unsettlePrincipalInterest;
        this.unsettleInterest = unsettleInterest;
        this.repaymentStatus =1;
        this.orderStatus = 1;
        this.associatedOrderStatus = "正常";
    }
}
