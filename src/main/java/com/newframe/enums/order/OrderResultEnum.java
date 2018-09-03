package com.newframe.enums.order;

import com.newframe.enums.CodeStatus;

/**
 * @author kfm
 * @date 2018.08.30 16:55
 */
public enum OrderResultEnum implements CodeStatus {
    // 成功
    SUCCESS("200","成功"),
    // 查不到物流信息
    NO_EXPRESS_INFO("205","暂无快递信息"),
    PARAM_ERROR("206","参数非法"),
    ORDER_UNDERWAY("207","订单进行中，不可删除"),
    FINANCINGABLE("200","账户余额充足，可以进行融资"),
    NO_FINANCINGABLE("208","账户余额不足融资金额的15%，不可融资"),;

    String code;

    String message;

    OrderResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
