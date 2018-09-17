/*
 * Copyright (C) 2016  HangZhou YuShi Technology Co.Ltd  Holdings Ltd. All rights reserved
 *
 * 本代码版权归杭州宇石科技所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.newframe.enums.account;



/**
 * 流水类型
 * 1：充值
 * 2：提现
 * 3：融资购机
 * 4：租机
 * 5：还款（正常还款）
 * 6：逾期还款（包括利息）
 * 7：付首期（包括保险）
 * 8：账户间不同种类金额划转（例如将可以余额部分划转到保证金）
 * 例如：付首期700块钱，700中包括200保险
 * dealAmount 700 这个是操作数据库的实际金额
 * extraAmount 这个是200 仅起记录作用，不操作数据库
 */
public enum DealTypeEnum {

	RECHARGE(1, "充值"),
    WITHDRAW(2, "提现"),
    FINANCING(3, "融资购机"),
    RENT(4, "租机"),
    NORMALPAY(5, "正常还款"),
    OVERDUEPAY(6, "逾期还款"),
    PAYFRIST(7, "付首期"),
    ACCOUNTTRANSFER(8, "账户间划转")
    ;

	private Integer code;
	private String message;

	private DealTypeEnum(Integer code, String message){
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


	public static DealTypeEnum getEnum(Integer vCodeType) {
		for (DealTypeEnum dealTypeEnum: DealTypeEnum.values()) {
			if (dealTypeEnum.getCode().equals(vCodeType)) {
				return dealTypeEnum;
			}
		}
		return null;
	}
	
	
}