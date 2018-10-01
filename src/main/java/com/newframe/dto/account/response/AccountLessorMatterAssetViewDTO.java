/**
 * Copyright 2018 bejson.com
 */
package com.newframe.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Auto-generated: 2018-08-30 18:18:16
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class AccountLessorMatterAssetViewDTO {
    /**
     * 累计应付租金
     */
    private BigDecimal totalPayableAmount;
    /**
     * 已付租金
     */
    private BigDecimal payedAmount;
    /**
     * 待付租金
     */
    private BigDecimal unpayAmount;
}