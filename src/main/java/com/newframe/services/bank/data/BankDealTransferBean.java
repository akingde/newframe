package com.newframe.services.bank.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 交易处理参数
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Builder
public class BankDealTransferBean {
    private String accno;       //银行卡号
    private String accnoname;  //银行名称
    private String useof;      //备注
    private String pevvaccname;//企业名称
    private String pecvopenaccdept;//支行名称
    private Double amount;    //提现，充值的金额
}
