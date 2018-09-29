package com.newframe.enums.merchant;

import com.newframe.enums.CodeStatus;

/**
 * @author kfm
 * @date 2018.09.14 16:26
 */
public enum MerchantResult implements CodeStatus {
    // 租赁商不存在
    RENTER_IS_NOT_EXIST("400102","租赁商不存在"),
    VALID_EXCEPTION("400101","参数校验错误"),
    MERCHANT_ORDER_EXIST("400103","订单已存在"),
    MERCHANT_ORDER_NOT_EXIST("400104","订单不存在"),
    RETURN_ADDRESS_NO_SETTING("400105","收货地址未设置，请联系91易租平台客服"),
    ;
    String code;
    String message;

    MerchantResult(String code, String message) {
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
