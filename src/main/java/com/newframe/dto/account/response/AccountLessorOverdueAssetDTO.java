/**
 * Copyright 2018 bejson.com
 */
package com.newframe.dto.account.response;

import lombok.Data;

/**
 * Auto-generated: 2018-08-31 13:52:41
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class AccountLessorOverdueAssetDTO {
    /**
     * 逾期资产合计
     */
    private Double totalOverdueAmount;
    /**
     * 逾期笔数
     */
    private Integer overdueNumber;
    /**
     * 逾期率
     */
    private Double overdueRate;

    /**
     * 本月应收总额
     */
    private Double monthPayableAmount;
}