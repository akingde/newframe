package com.newframe.services.bank.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 银行交易处理结果查询参数
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Builder
public class BankDealQueryBean {
    private String serialnumber;//请求id
    private String bankCard;    //银行卡号
}
