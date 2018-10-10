/*
 * Copyright (C) 2016  HangZhou YuShi Technology Co.Ltd  Holdings Ltd. All rights reserved
 *
 * 。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.newframe.enums.User;

/**
 * 【验证码类型】
 * @author wangdong
 */
public enum PassWordTypeEnum {

	LOGINPWD(1, "登陆密码"),
	PAYPWD(2, "支付密码"),
    ;

	private Integer code;
	private String message;

	private PassWordTypeEnum(Integer code, String message){
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


	public static PassWordTypeEnum getEnum(Integer vCodeType) {
		for (PassWordTypeEnum passWordTypeEnum : PassWordTypeEnum.values()) {
	    	if (passWordTypeEnum.getCode().equals(vCodeType)) {
	    		return passWordTypeEnum;
			}
		}
		return null;
	}
}
