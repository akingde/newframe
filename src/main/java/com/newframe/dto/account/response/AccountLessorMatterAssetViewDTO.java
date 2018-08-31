/**
 * Copyright 2018 bejson.com
 */
package com.newframe.dto.account.response;

import lombok.Data;

/**
 * Auto-generated: 2018-08-30 18:18:16
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class AccountLessorMatterAssetViewDTO {
    /**
     * 投资回报率
     */
    private double InvestReturnRate;
    /**
     * 市场平均投资回报率
     */
    private double AverageInvestReturnRate;
    /**
     * 租赁总额
     */
    private double totalRentAmount;
    /**
     * 累计应付租金
     */
    private double totalPayableAmount;
    /**
     * 已付租金
     */
    private double payedAmount;
    /**
     * 待付租金
     */
    private double unPayAmount;
}