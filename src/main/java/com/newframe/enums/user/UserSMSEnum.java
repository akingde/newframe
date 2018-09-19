package com.newframe.enums.user;

import static com.newframe.enums.sms.AliSmsVarEnum.*;

/**
 * @author WangBin
 */
public enum UserSMSEnum {

    REGISTER(1, NEW_USER_REGISTER_LOGIN.getTemplateCode()),
    LOGIN(2, OLD_USER_LOGIN.getTemplateCode()),
    SET_PASSWORD(3, ""),
    FORGET_PASSWORD(4, ""),
    MODIFY_MOBILE(5, ""),
    SET_BANKCARD(6, ADD_BANK_CARD.getTemplateCode())
    ;

    private Integer codeType;
    private String templateCode;

    UserSMSEnum(Integer codeType, String templateCode) {
        this.codeType = codeType;
        this.templateCode = templateCode;
    }
}
