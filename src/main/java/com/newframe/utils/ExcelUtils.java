package com.newframe.utils;

import org.apache.poi.ss.formula.eval.OperandResolver;
import org.apache.poi.ss.formula.functions.Finance;
import org.apache.poi.ss.formula.functions.FinanceLib;
import org.apache.poi.ss.formula.functions.PPMT;

import java.math.BigDecimal;

/**
 * @author WangBin
 */
public class ExcelUtils {

    public static double ppmt(double rate, int per, int nper, double pv){
        return Math.abs(Finance.ppmt(rate, per, nper, pv));
    }

    public static double ipmt(double rate, int per, int nper, double pv){
        return Math.abs(Finance.ipmt(rate, per, nper, pv));
    }

    public static double pv(double rate, double nper, double pmt, double fv, boolean type){
        return Math.abs(FinanceLib.pv(rate, nper, pmt, (float)fv, type));
    }

    public static double pv(double rate, double nper, double pmt, double fv){
        return Math.abs(FinanceLib.pv(rate, nper, pmt, (float)fv, false));
    }

    public static double pv(double rate, double nper, double pmt, boolean type){
        return Math.abs(FinanceLib.pv(rate, nper, pmt, 0.0F, type));
    }

    public static double pv(double rate, int nper, double pmt){
        return Math.abs(FinanceLib.pv(rate, (double)nper, pmt, 0.0F, false));
    }

    public static void main(String[] args) {
        System.out.println(pv(0.15/12, 10, 200));
    }
}
