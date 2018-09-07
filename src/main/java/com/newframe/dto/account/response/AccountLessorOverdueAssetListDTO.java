/**
 * Copyright 2018 bejson.com
 */
package com.newframe.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Auto-generated: 2018-08-31 13:53:17
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class AccountLessorOverdueAssetListDTO {
    private Integer ctime;
    private Integer utime;
    private Long orderId;
    /**
     * 投资的方式。1:融资购机，2:租机
     */
    private Integer investType;
    /**
     * 投资金额
     */
    private BigDecimal investAmount;
    /**
     * 投资期限
     */
    private BigDecimal investMonth;
    /**
     * 已还金额
     */
    private BigDecimal payedAmount;
    /**
     * 待还金额
     */
    private BigDecimal unpayAmount;
    /**
     * 逾期天数
     */
    private Integer overdueDays;
    /**
     * 还款方式。1:押金贷，2:按月
     */
    private Integer payType;
    /**
     * 订单状态。1:正常，2:逾期，3:催收中，4:催收成功，5:坏账
     */
    private Integer orderStatus;
}