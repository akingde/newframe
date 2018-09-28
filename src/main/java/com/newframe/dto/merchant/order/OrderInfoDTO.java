package com.newframe.dto.merchant.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.28 11:46
 */
@Data
public class OrderInfoDTO {
    private Integer orderType;
    private Integer orderStatus;
    private FinancingInfo financingInfo;
    private RentInfo rentInfo;
    @Data
    public class FinancingInfo{
        /**融资金额*/
        private BigDecimal financingAmount;
        /**还款周期*/
        private Integer numberOfPeriods;
        /**残值保障计划金额*/
        private BigDecimal residualAmount;
        /**是否参加残值保障计划，1：参加，0：不参加*/
        private Integer residualScheme;
        /**保证金*/
        private BigDecimal deposit;
    }

    @Data
    public class RentInfo{
        private BigDecimal accidentBenefit;
        private Integer numberOfPeriods;
        private Integer patternPayment;
        private BigDecimal monthPayment;
        private BigDecimal downPayment;
        private BigDecimal orderAmount;
    }
}


