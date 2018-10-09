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
public enum ApplyStatusEnum {

	ADDSHEET(0, "添加材料"),
	CHECKPENDING(1, "待审核"),
	FIRSTTRIAL_FAIL(2,"初审失败"),
	SECONDTRIAL(3,"待复审"),
	SECONDTRIAL_SUCCESS(4,"复审成功"),
	SECONDTRIAL_FAIL(5,"复审失败")
    ;

	private Integer code;
	private String message;

	private ApplyStatusEnum(Integer code, String message){
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


	public static ApplyStatusEnum getEnum(Integer vCodeType) {
		for (ApplyStatusEnum applyStatusEnum : ApplyStatusEnum.values()) {
	    	if (applyStatusEnum.getCode().equals(vCodeType)) {
	    		return applyStatusEnum;
			}
		}
		return null;
	}
}
