package com.newframe.services.order.impl;

import com.newframe.services.order.FormulaService;
import org.apache.poi.ss.formula.functions.Finance;
import org.apache.poi.ss.formula.functions.FinanceLib;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 订单中涉及到的一些公式封装
 * @author kfm
 * @date 2018.09.30 10:05
 */
@Service
public class FormulaServiceImpl implements FormulaService {

    DecimalFormat format = new DecimalFormat("#.##");

    /**
     * 计算等额本息还款方式需要还的本金有多少
     * @param rate 年利率
     * @param periods 期数
     * @param monthPayment 月租金
     * @return 计算结果
     */
    @Override
    public BigDecimal getAveragePrincipal(double rate, Integer periods, BigDecimal monthPayment){
        double averagePrincipal = FinanceLib.pv(rate/12,periods,monthPayment.doubleValue(),0,false)*(-1);
        return new BigDecimal(format.format(averagePrincipal));
    }

    /***
     * 计算到期一次性支付的本金
     * @param financeAmount 融资金额（本金）
     * @param averagePrincipal 等额本息还款本金
     * @return 计算结果
     */
    @Override
    public BigDecimal getOnePrincipal(BigDecimal financeAmount, BigDecimal averagePrincipal){
         return financeAmount.subtract(averagePrincipal);
    }

    /**
     * 计算到期一次性支付的本息之和
     * @param financeAmount 融资金额
     * @param averagePrincipal 等额本息支付本金
     * @param rate 年利率
     * @param periods 还款期数
     * @return 计算结果
     */
    @Override
    public BigDecimal getSumAmount(BigDecimal financeAmount, BigDecimal averagePrincipal, double rate, Integer periods) {
        BigDecimal rateOfReturn = new BigDecimal(1+(rate/360)*periods*30);
        return financeAmount.subtract(averagePrincipal).multiply(rateOfReturn).setScale(2,RoundingMode.HALF_UP);
    }
}
