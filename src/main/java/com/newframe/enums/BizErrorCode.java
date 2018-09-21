
package com.newframe.enums;

/**
 *【错误码枚举】
 * 业务错误状态码有6位，都是以40开头，中间两位表未业务（01：游戏，02：）
 *
 * @author
 */
public enum BizErrorCode implements CodeStatus {

    PARAM_INFO_ERROR("400101","参数非法"),
    SAVE_INFO_ERROR("400102","保存信息失败"),
    NOT_LOGIN("400103","未登录"),
    ACCOUNT_NOTEXIST("400104","账户不存在"),
    ACCOUNTTYPE_NOTEXIST("400105","账户类型不存在"),
    ACCOUNTREPAY_NOTEXIST("400106","该笔还款计划不存在"),
    NOT_SUFFICIENT_FUNDS("400106","该笔还款计划不存在")
    ;
    private String code;
    private String message;

    private BizErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
