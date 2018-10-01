package com.newframe.dto.order.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.10.01 10:03
 * 融资信息封装
 */
@Data
public class FinancingInfo {
    /**等额本息支付方式本金*/
    private BigDecimal averagePrincipal;
    /**年化收益率*/
    private BigDecimal rate;
    /**月还款金额*/
    private BigDecimal monthPayment;
    /**一次性支付金额本金*/
    private BigDecimal onePrincipal;
    /**一次性支付金额本息之和*/
    private BigDecimal sumAmount;
}
