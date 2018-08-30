package com.newframe.enums.order;

import com.newframe.enums.CodeStatus;

/**
 * @author kfm
 * @date 2018.08.30 16:55
 */
public enum OrderResultEnum implements CodeStatus {
    // 成功
    SUCCESS("200","成功");

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
