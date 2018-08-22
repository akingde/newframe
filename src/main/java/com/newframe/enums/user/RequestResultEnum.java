package com.newframe.enums.user;

import com.newframe.enums.CodeStatus;

/**
 * @author WangBin
 */
public enum RequestResultEnum implements CodeStatus {

    MOBILE_INVALID("10000", "无效的手机号"),
    MOBILE_EXISTS("10001", "手机号已存在"),
    MOBILE_NOT_EXISTS("10002", "手机号不存在"),
    PASSWORD_INVALID("10003", "密码无效"),
    PASSWORD_NOT_EXISTS("10004", "密码不存在"),
    PASSWORD_EXISTS("10005", "密码已存在"),
    PASSWORD_AGREEMENT("10006", "新密码不能与原密码一致"),
    PASSWORD_NOT_AGREEMENT("10009", "两次输入密码不一致"),
    PASSWORD_ERROR("10008", "密码错误"),
    VERIFICATION_CODE_INVALID("11008", "验证码无效"),
    CHECK_CODE_ERROR("11009", "验证码认证失败"),
    LOGIN_ERROR("11010", "登录失败"),

    ADDRESS_NOT_EXISTS("20000", "地址不存在"),
    ADDRESS_ERROR("20001", "地址错误"),

    USER_EXISTS("30001", "用户已存在"),
    USER_NOT_EXISTS("30002", "用户不存在"),

    ROLE_NOT_EXEISTS("40000", "未拥有此角色"),
    ROLE_APPLY_ERROR("40001", "角色申请失败"),
    ROLE_REVOKE_RPPLY_ERROR("40003", "角色申请撤销失败"),
    ROLE_APPLY_TOO_MUCH("40002", "有角色正在申请"),

    RENT_MERCHANT_ADD_ERROR("50000", "供应商添加失败"),
    RENT_MERCHANT_EXISTS("50001", "此供应商已隶属于其他人"),

    APPOINT_UNAUTHORIZED("60000", "未授权指定供应商功能"),
    APPOINT_COUNT_TOO_MUCH("60001", "一次最多指定10个供应商"),

    FILE_TOO_MUCH("70000", "文件过多"),
    FILE_NOT_EXISTS("70001", "必须的文件不存在"),
    FILE_FORMAT_ERROR("70002", "文件格式错误"),
    ILLEGAL_FILE("70003", "文件参数不合法"),

    PARAMETER_LOSS("80001", "缺少必须的参数"),
    ID_NUMBER_ERROR("80002", "身份证号不正确"),

    INVALID_ACCESS("100000", "无效的访问"),
    ;

    private String code;
    private String message;

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    RequestResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    }