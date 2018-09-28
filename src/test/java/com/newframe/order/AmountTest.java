package com.newframe.order;

import org.apache.poi.ss.formula.functions.Finance;
import org.junit.Test;

import java.text.DecimalFormat;

/**
 * @author kfm
 * @date 2018.09.27 17:56
 */
public class AmountTest {

    @Test
    public void rentPrice(){
        Integer buyPrice = 10000;
        DecimalFormat format = new DecimalFormat("#.##");
        System.out.println(format.format(Finance.pmt(0.15/12,24,Double.valueOf(buyPrice),0,1)));
    }
}
