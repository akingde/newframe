package com.newframe.enums.merchant;

import com.newframe.enums.CodeStatus;

/**
 * @author kfm
 * @date 2018.09.14 16:26
 */
public enum MerchantResult implements CodeStatus {
    // 租赁商不存在
    RENTER_IS_NOT_EXIST("400101","租赁商不存在")
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
