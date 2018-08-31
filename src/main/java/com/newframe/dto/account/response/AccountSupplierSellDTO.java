/**
 * Copyright 2018 bejson.com
 */
package com.newframe.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Auto-generated: 2018-08-31 15:13:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class AccountSupplierSellDTO {
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