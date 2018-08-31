
package com.newframe.enums;

/**
 *【错误码枚举】
 *
 * @author
 */
public enum SystemCode implements CodeStatus {

    SUCC("000", "成功"),
    SUCCESS("200", "成功"),
    NEED_LOGIN("201", "未登录"),
    BAD_REQUEST("202", "参数非法"),
    NOT_IN_WHITELIST("203", "不在白名单内"),
    ILLEGAL_ACTION("204", "不合法的接口"),
    HANDLE_EXCEPTION("205", "系统处理异常，请稍后再试"),
    NO_PAGE_PARAM("202","参数非法，缺少分页参数"),
    ORDER_FINANCING_FAIL("206","订单融资超过最大次数限制，不允许再次融资"),
    ORDER_CANCEL_FAIL("207","订单取消失败"),
    LOAN_ORDER_STATUS_ERROR("208","订单状态错误，不能进行放款"),
    LOAN_FAIL("208","放款失败"),
    FILE_UPLOAD_FAIL("205","文件上传失败"),
    GENERATE_SUPPLY_ORDER_FAIL("205","生成供应商订单失败，请稍后再试"),
    GENERATE_SUPPLY_ORDER_SUCCESS("200","确认放款成功，供应商订单已生成"),










    ;





    private String code;

    private String message;

    private SystemCode(String code, String message) {
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
