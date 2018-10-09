package com.newframe.enums;

/**
 *【错误码枚举】
 * 业务错误状态码有6位，都是以40开头，中间两位表未业务（01：游戏，05：用户）
 *
 * @author
 */
public enum BizErrorCode implements CodeStatus {


    PARM_ERROR("400101", "参数非法"),
    SAVE_INFO_ERROR("400102","没有更多消息了"),
    MCODE_SEND_ERROR("400103", "发送短信失败,请稍后再试"),
    MCODE_TYPE_ERROR("400104", "短信类型错误,请稍后再试"),
    MCODE_ONE_MINIT_LIMIT("400105", "验证码发送太频繁，请1分钟后重试"),
    MCODE_VERIFY_ERROR("400106", "验证码已用过或错误")
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
