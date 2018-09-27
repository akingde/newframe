package com.newframe.entity.account;

import com.newframe.dto.account.RentMachineStatistics;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * <p>
 * </p>
 *
 * @author wangdong
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountRenterRentMachine {
    /**
     * uid
     */
    @Id
    private Long uid;
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

    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;

    public void setAccountRenterRentMachine(Long uid,BigDecimal rentAccount,BigDecimal totalPayableAccount,BigDecimal payedAccount,BigDecimal unpayAccount){
        this.uid = uid;
        this.rentAccount=rentAccount;
        this.totalPayableAccount=totalPayableAccount;
        this.payedAccount=payedAccount;
        this.unpayAccount=unpayAccount;
    }

    public void setAccountRenterRentMachine(Long uid, RentMachineStatistics rentMachineStatistics){
        this.uid = uid;
        this.rentAccount=rentMachineStatistics.getRentAccount();
        this.totalPayableAccount=rentMachineStatistics.getTotalPayableAccount();
        this.payedAccount=rentMachineStatistics.getPayedAccount();
        this.unpayAccount=rentMachineStatistics.getUnpayAccount();
    }
}
