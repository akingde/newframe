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
    private Long associatedOrderId;
    /**
     * 融资金额
     */
    private BigDecimal financingAmount;
    /**
     * 融资期限
     */
    private Integer financingMaturity;

    /**
     * 融资本息
     */
    private BigDecimal financingPrincipalInterest;

    /**
     * 融资利息
     */
    private BigDecimal financingInterest;

    /**
     * 已经偿还的本息
     */
    private BigDecimal settlePrincipalInterest;

    /**
     * 已经偿还的利息
     */
    private BigDecimal settleInterest;

    /**
     * 待偿还的本息
     */
    private BigDecimal unsettlePrincipalInterest;

    /**
     * 待偿还的利息
     */
    private BigDecimal unsettleInterest;

    /**
     * 还款状态，1:正常，2:逾期，3:逾期已经结清
     */
    private Integer repaymentStatus;

    /**
     * 关联订单的状态。1:正常，2:逾期，3:逾期未催收，4:催收中，5:催收已还机
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
}
