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
    private Integer financingDeadline;
    private BigDecimal deposit;
    private Long supplierId;
    private String supplierName;
    private Integer financingTime;
    private Integer residualScheme;
}
