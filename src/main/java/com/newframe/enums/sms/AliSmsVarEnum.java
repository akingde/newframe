package com.newframe.enums.sms;

/**
 * 【阿里云短信变量】
 *
 * @author yangjh
 */
public enum AliSmsVarEnum {

    //游戏上架通知预约用户
    USER_GAME_APPOINTMENT("SMS_83850008", "gameName", "osType"),
    MOBILE_UNBIND_NOTIFY("SMS_122290217", "newAccount", "oldAccount"),
    APPLY_PASS_FOR_MEMBER("SMS_100785088", "name", null),
    APP_UNBIND_THIRDLOGIN("SMS_122290675", "account", null),
    TO_BE_LEADER("SMS_99195081", null, null),

    // 回调金额与实际应付金额不一致
    TRADE_AMOUNT_VALID("SMS_126781290", "amount", "payFlowId"),
    // 回调金额超过支付限制
    TRADE_AMOUNT_LIMIT("SMS_126970796", "amount", "orderId"),
    /*******************手机租赁********************/
    // 优惠券到期提醒,参数：优惠券张数，金额
    COUPON_EXPIRE_NOTICE("SMS_139986883","couponsTotal","amount"),
    //还机成功通知
    RETURN_SUCCESS("SMS_139977069",null,null),
    //自动扣除租金,参数：扣除的是几月的租金
    AUTO_DEDUCT_RENT("SMS_139972045","month",null),
    //发货成功
    DELIVER_GOODS_SUCCESS("SMS_139977068",null,null),
    //下单审核通过
    ORDER_REVIEW_SUCCESS("SMS_139986876",null,null),
    //账户余额不足,自动扣除租金失败时发送，参数：哪个月的租金扣除失败
    INSUFFICIENT_ACCOUNT_BALANCE("SMS_139977062","month",null),
    // 下单审核不通过
    ORDER_REVIEW_FAIL("SMS_139977058",null,null),
    // 订单取消
    ORDER_CANCEL("SMS_139977054",null,null),
    // 下单首付付款成功
    ORDER_DOWN_PAYMENT_SUCCESS("SMS_139982088",null,null),
    //用户修改手机号验证码,参数：随机验证码
    USER_CHANGE_PHONE("SMS_139972027","code",null),
    //老用户登陆验证码
    OLD_USER_LOGIN("SMS_139977044","code",null),
    //新用户注册及登陆验证码
    NEW_USER_REGISTER_LOGIN("SMS_139986859","code",null),
    //注册成功短信通知
    REGISTER_SUCCESS("SMS_139977034",null,null),
    //新用户注册短信验证
    NEW_USER_REGISTER("SMS_139986850","code",null),
    /*******************黄金交易所*************************/
    //用户注册验证码
    USER_REGISTER("SMS_119525052","code",null),
    //登录确认验证码
    USER_LOGIN("SMS_119525054","code",null),
    //信息变更验证码
    USER_CHANGE_INFO("SMS_119525050","code",null),
    //资产变动验证码
    USER_PROPERTY_CHANGE("SMS_136393159","code",null)
    ;
    private String templateCode;
    private String varOne;
    private String varTwo;

    AliSmsVarEnum(String templateCode, String varOne, String varTwo) {
        this.templateCode = templateCode;
        this.varOne = varOne;
        this.varTwo = varTwo;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getVarOne() {
        return varOne;
    }

    public void setVarOne(String varOne) {
        this.varOne = varOne;
    }

    public String getVarTwo() {
        return varTwo;
    }

    public void setVarTwo(String varTwo) {
        this.varTwo = varTwo;
    }

    public static AliSmsVarEnum getEnum(String templateCode) {
        for (AliSmsVarEnum aliSmsVarEnum : AliSmsVarEnum.values()) {
            if (aliSmsVarEnum.getTemplateCode().equals(templateCode)) {
                return aliSmsVarEnum;
            }
        }
        return null;
    }
}
