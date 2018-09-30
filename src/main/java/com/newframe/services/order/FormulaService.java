package com.newframe.services.order;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.30 10:04
 */
public interface FormulaService {
    /**
     * 计算等额本息还款方式需要还的本金有多少
     * @param rate 年利率
     * @param periods 期数
     * @param monthPayment 月租金
     * @return 计算结果
     */
    BigDecimal getAveragePrincipal(double rate, Integer periods, BigDecimal monthPayment);

    /**
     * 计算到期一次性支付的本金
     * @param financeAmount 融资金额（本金）
     * @param averagePrincipal 等额本息还款本金
     * @return 计算结果
     */
    BigDecimal getOnePrincipal(BigDecimal financeAmount, BigDecimal averagePrincipal);

    /**
     * 计算到期一次性支付的本息之和
     * @param financeAmount 融资金额
     * @param averagePrincipal 等额本息支付本金
     * @param rate 年利率
     * @param periods 还款期数
     * @return 计算结果
     */
    BigDecimal getSumAmount(BigDecimal financeAmount,BigDecimal averagePrincipal,double rate,Integer periods);
}
