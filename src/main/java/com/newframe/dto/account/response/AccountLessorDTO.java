/**
 * Copyright 2018 bejson.com
 */
package com.newframe.dto.account.response;

import lombok.Data;

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
    private long useableAmount;
    /**
     * 资产总额
     */
    private long totalAssets;
    /**
     * 冻结资产
     */
    private long frozenAssets;
    private long uid;
    private long ctime;
    private long utime;
    /**
     * 保证金
     */
    private double cashDeposit;
    /**
     * 待收金额
     */
    private double payAccount;
    /**
     * 每月应收金额
     */
    private double monthPayableAccount;
}