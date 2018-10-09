/*
 * Copyright (C) 2016  HangZhou YuShi Technology Co.Ltd  Holdings Ltd. All rights reserved
 *
 * 。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.newframe.enums.sms;

/**
 * 【验证码类型】
 * @author wangdong
 */
public enum McodeTypeEnum {

	REGISTER(1, "注册"),
	LOGIN(2, "登录"),
	REGISTER_OR_LOGIN(3,"注册或者登录"),
	CHANGE_USERINFO(4, "修改用户的重要信息"),
	USER_PROPERTY_CHANGE(5,"资产变动验证码")
    ;

	private Integer code;
	private String message;

	private McodeTypeEnum(Integer code, String message){
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	public static McodeTypeEnum getEnum(Integer vCodeType) {
		for (McodeTypeEnum vCodeTypeEnum : McodeTypeEnum.values()) {
	    	if (vCodeTypeEnum.getCode().equals(vCodeType)) {
	    		return vCodeTypeEnum;
			}
		}
		return null;
	}
}
