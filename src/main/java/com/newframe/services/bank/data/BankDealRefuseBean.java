package com.newframe.services.bank.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 交易拒绝参数
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Builder
public class BankDealRefuseBean {
    private String tranflow;    //流水编号
    private String accno;       //银行卡号
    private String accnoname;  //大账户银行名称
}
