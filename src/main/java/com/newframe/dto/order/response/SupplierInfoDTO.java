package com.newframe.dto.order.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.08.31 16:37
 */
@Data
public class SupplierInfoDTO {
    private Long supplierId;
    private String supplierName;
    /**融资金额*/
    private BigDecimal financingAmount;
    /**等额本息月付金额*/
    private BigDecimal monthPayment;
    /**等额本息付款方式总共要付的本金*/
    private BigDecimal averagePrincipal;
    /**残值保障计划金额*/
    private BigDecimal accidentBenefit;
    /**首付金额*/
    private BigDecimal downPayment;
    /**残值保障金金额*/
    private BigDecimal deposit;
    /**到期一次性支付本金*/
    private BigDecimal onePrincipal;
    /**到期一次性支付本息合计*/
    private BigDecimal sumAmount;
}
