package com.newframe.utils;

import org.apache.poi.ss.formula.functions.Finance;
import org.apache.poi.ss.formula.functions.FinanceLib;
import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * @author WangBin
 */
public class ExcelUtils {

    public static BigDecimal ppmt(double rate, int per, int nper, double pv){
        return check(Math.abs(Finance.ppmt(rate, per, nper, pv)));
    }

    public static BigDecimal ipmt(double rate, int per, int nper, double pv){
        return check(Math.abs(Finance.ipmt(rate, per, nper, pv)));
    }

    public static BigDecimal pmt(double rate, int nper, double pv){
        return check(Math.abs(Finance.pmt(rate, nper, pv)));
    }

    public static BigDecimal pv(double rate, double nper, double pmt, double fv, boolean type){
        return check(Math.abs(FinanceLib.pv(rate, nper, pmt, (float)fv, type)));
    }

    public static BigDecimal pv(double rate, double nper, double pmt, double fv){
        return check(Math.abs(FinanceLib.pv(rate, nper, pmt, (float)fv, false)));
    }

    public static BigDecimal pv(double rate, double nper, double pmt, boolean type){
        return check(Math.abs(FinanceLib.pv(rate, nper, pmt, 0.0F, type)));
    }

    public static BigDecimal pv(double rate, int nper, double pmt){
        return check(Math.abs(FinanceLib.pv(rate, (double)nper, pmt, 0.0F, false)));
    }

    private static BigDecimal check(double value){
        DecimalFormat format = new DecimalFormat("#.##");
        return BigDecimal.valueOf(Double.valueOf(format.format(value)));
    }

    public static void main(String[] args) {
        System.out.println(pv(0.15/12, 10, 200));
    }

}
