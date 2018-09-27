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
        return Finance.ppmt(rate, per, nper, pv);
    }

    public static double pv(double rate, double nper, double pmt, double fv, boolean type){
        return FinanceLib.pv(rate, nper, pmt, fv, type);
    }

    public static double pv(double rate, double nper, double pmt, double fv){
        return FinanceLib.pv(rate, nper, pmt, fv, false);
    }

    public static double pv(double rate, double nper, double pmt, boolean type){
        return FinanceLib.pv(rate, nper, pmt, 0.0D, type);
    }

    public static double pv(double rate, int nper, double pmt){
        return FinanceLib.pv(rate, (double)nper, pmt, 0.0D, false);
    }
}
