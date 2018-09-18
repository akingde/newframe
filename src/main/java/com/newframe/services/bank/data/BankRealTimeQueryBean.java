package com.newframe.services.bank.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 查询实时交易
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Builder
public class BankRealTimeQueryBean {//此类只针对提现对接接口
	private String accountid;    //大账户id
	private int flag ;        //0提现1充值
	private List<String> tranflows;  //流水编号
}