package com.newframe.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author WangBin
 */
public class BankCardUtils {

    public static  boolean checkBankCard(String nonCheckCodeCardId){
        if(StringUtils.isEmpty(nonCheckCodeCardId)){
            return false;
        }
        char res = getBankCardCheckCode(nonCheckCodeCardId.substring(0, nonCheckCodeCardId.length() - 1));
        if(res!='N'){
            String charJX = nonCheckCodeCardId.substring(nonCheckCodeCardId.length()-1);
            if(charJX.equals(String.valueOf(res))){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * 该校验的过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，则将其减去9），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     */
    private static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(!nonCheckCodeCardId.matches("\\d+")||nonCheckCodeCardId.trim().length()<15
                ||nonCheckCodeCardId.trim().length()>18) {
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        // 执行luh算法
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {  //偶数位处理
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
}
