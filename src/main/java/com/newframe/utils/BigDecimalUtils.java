package com.newframe.utils;

import java.math.BigDecimal;

/**
 * @author WangBin
 */
public class BigDecimalUtils {

    public static boolean compareTo(BigDecimal val){
        if (val == null){
            return false;
        }
        if(val.compareTo(BigDecimal.ZERO) != 1){
            return false;
        }
        return true;
    }
}
