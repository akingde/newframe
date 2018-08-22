package com.newframe.enums.order;

/**
 * @author kfm
 * 支付方式枚举
 */
public enum  PatternPaymentEnum {
    //全款支付
    FULL_PAYMENT(1),
    // 分期支付
    INSTALMENT_PAYMENT(2)
    ;


    private Integer value;
    private PatternPaymentEnum(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static boolean isDefined(Integer value){
        PatternPaymentEnum[] patternPaymentEnums = PatternPaymentEnum.values();
        for(PatternPaymentEnum patternPaymentEnum:patternPaymentEnums){
            if(patternPaymentEnum.getValue().equals(value)){
                return true;
            }
        }
        return false;
    }
}
