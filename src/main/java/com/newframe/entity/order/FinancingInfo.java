package com.newframe.entity.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.01 13:53
 */
@Data
public class FinancingInfo {
    private BigDecimal financingAmount;
    private BigDecimal accidentBenefit;
    private int financingDeadline;
    private BigDecimal deposit;
    private long supplierId;
    private String supplierName;
    private Integer financingTime;
}
