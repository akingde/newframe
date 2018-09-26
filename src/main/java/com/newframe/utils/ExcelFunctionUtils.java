package com.newframe.utils;

import org.apache.poi.ss.formula.eval.OperandResolver;
import org.apache.poi.ss.formula.functions.Finance;

/**
 * @author WangBin
 */
public class ExcelFunctionUtils {

    public static double ppmt(double rate, int per, int nper, double pv){
        return Finance.ppmt(rate, per, nper, pv);
    }
}
