package com.newframe.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 资金方账户表
 * </p>
 *
 * @author zww
 * @since 2018-08-29
 */
@Data
public class AccountFundingDTO {
    /**
     * uid
     */
    private Long uid;
    /**
     * 可用金额
     */
    private BigDecimal useableAmount;
    /**
     * 资产总额
     */
    private BigDecimal totalAssets;
    /**
     * 冻结资产
     */
    private BigDecimal frozenAssets;
    /**
     * 保证金
     */
    private BigDecimal cashDeposit;
    /**
     * 待收金额
     */
    private BigDecimal dueInAmount;
    /**
     * 本月应收
     */
    private BigDecimal monthPayableAmount;
    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;


    /**
     * '逾期金额合计(元)'
     */
    private BigDecimal overdueAmount;

    /**
     * '逾期笔数'
     */
    private Integer overdueCount;
    /**
     * '逾期率'
     */
    private Double overdueRate;
}
