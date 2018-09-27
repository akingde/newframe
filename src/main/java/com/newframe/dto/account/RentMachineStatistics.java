package com.newframe.dto.account;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author:wangdong
 * @description:
 */
@Data
public class RentMachineStatistics {

    /**
     * 租赁总额
     */
    private BigDecimal rentAccount;
    /**
     * 累计应付租金
     */
    private BigDecimal totalPayableAccount;
    /**
     * 已付租金
     */
    private BigDecimal payedAccount;
    /**
     * 代付租金
     */
    private BigDecimal unpayAccount;

    public void setRentMachineStatistics(BigDecimal rentAccount,BigDecimal totalPayableAccount,BigDecimal payedAccount,BigDecimal unpayAccount){
        this.rentAccount=rentAccount;
        this.totalPayableAccount=totalPayableAccount;
        this.payedAccount=payedAccount;
        this.unpayAccount=unpayAccount;
    }
}
