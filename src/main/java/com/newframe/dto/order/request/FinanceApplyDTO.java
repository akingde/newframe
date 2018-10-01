package com.newframe.dto.order.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.30 18:07
 */
@Data
public class FinanceApplyDTO {
    private Long orderId;
    private Long supplierId;
    private BigDecimal financingAmount;
    private Integer financingDeadline;
    private Integer residualScheme;

    /**等额本息支付方式本金*/
    private BigDecimal averagePrincipal;
    /**一次性支付本金*/
    private BigDecimal onePrincipal;
    /**一次性支付本息之和*/
    private BigDecimal sumAmount;
}
