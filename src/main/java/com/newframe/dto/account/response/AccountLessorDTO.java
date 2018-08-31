/**
 * Copyright 2018 bejson.com
 */
package com.newframe.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Auto-generated: 2018-08-30 18:12:42
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class AccountLessorDTO {
    /**
     * 可用资产
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
    private long uid;
    private long ctime;
    private long utime;
    /**
     * 保证金
     */
    private BigDecimal cashDeposit;
    /**
     * 待收金额
     */
    private BigDecimal payAmount;
    /**
     * 每月应收金额
     */
    private BigDecimal monthPayableAmount;
}