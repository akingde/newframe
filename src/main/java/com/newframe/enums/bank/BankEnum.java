package com.newframe.enums.bank;

import org.apache.commons.lang3.StringUtils;

/**
 * @author WangBin
 */
public enum BankEnum {

    ICBC("中国工商银行"),
    ABC("中国农业银行"),
    BOC("中国银行"),
    CCB("中国建设银行"),
    BOCOM("交通银行"),
    ;

    private String bankName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    BankEnum(String bankName) {
        this.bankName = bankName;
    }

    public static boolean isEmpty(String bankName){
        for (BankEnum bankEnum : BankEnum.values()){
            if(StringUtils.equals(bankEnum.getBankName(), bankName)){
                return false;
            }
        }
        return true;
    }
}
