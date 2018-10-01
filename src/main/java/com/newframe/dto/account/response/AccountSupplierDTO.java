/**
 * Copyright 2018 bejson.com
 */
package com.newframe.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Auto-generated: 2018-08-31 15:9:24
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class AccountSupplierDTO {
    /**
     * 可用余额
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
     * 累计营收
     */
    private BigDecimal totalEarning;
    /**
     * 累计销售数量
     */
    private Integer saleNumber;
    /**
     * 待发货数量
     */
    private Integer deliverNumber;
    private Long uid;
}