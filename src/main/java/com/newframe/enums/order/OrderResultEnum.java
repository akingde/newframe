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
    NO_FINANCINGABLE("208","账户余额不足融资金额的15%，不可融资"),
    ORDER_RENTING_FAIL("206","订单租赁审核不通过超过最大次数限制，不允许再次租赁"),
    ORDER_AUDITTING("208","订单审核中"),
    ORDER_NO_EXIST("209","订单不存在"),
    LOAN_ORDER_STATUS_ERROR("210","订单状态错误，不能进行放款"),
    RENTER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT("401011","租赁商账户可用余额不足，扣款失败"),
    FUNDER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT("401011","资金方账户可用余额不足，扣款失败"),
    ACCOUNT_NO_EXIST("401012","账户不存在"),
    NO_HAVE_FUNDER("401013","资金方不存在"),
    LESSOR_NOT_EXIST("401014","此型号的手机无出租方出租"),
    LESSOR_PRICE_NOT_EXIST("401015","平台无此型号手机的出租价格，请联系客服添加"),
    ;

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
