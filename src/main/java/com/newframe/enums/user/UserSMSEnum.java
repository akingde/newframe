package com.newframe.enums.user;

import com.newframe.enums.sms.AliSmsVarEnum;


/**
 * @author WangBin
 */
public enum UserSMSEnum {

    REGISTER(1, AliSmsVarEnum.NEW_USER_REGISTER_LOGIN.getTemplateCode()), //注册
    LOGIN(2, AliSmsVarEnum.OLD_USER_LOGIN.getTemplateCode()),//已经注册过的登陆验证码
    SET_PASSWORD(3, AliSmsVarEnum.SETTING_PASSWORD.getTemplateCode()),//未登录设置密码
    FORGET_PASSWORD(4, AliSmsVarEnum.FORGET_PASSWORD.getTemplateCode()),//忘记密码
    MODIFY_MOBILE(5, AliSmsVarEnum.USER_CHANGE_PHONE.getTemplateCode()),//修改手机号
    ADD_BANKCARD(6, AliSmsVarEnum.ADD_BANK_CARD.getTemplateCode()),//添加银行卡
    ;

    private Integer codeType;
    private String templateCode;

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    UserSMSEnum(Integer codeType, String templateCode) {
        this.codeType = codeType;
        this.templateCode = templateCode;
    }

    public static UserSMSEnum checkType(Integer codeType){
        for (UserSMSEnum userSMSEnum : UserSMSEnum.values()) {
            if(codeType.equals(userSMSEnum.codeType)){
                return userSMSEnum;
            }
        }
        return null;
    }
}
