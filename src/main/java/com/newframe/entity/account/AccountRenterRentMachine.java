package com.newframe.entity.account;

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
     * 订单融资金额
     */
    private BigDecimal rentAccount;
    /**
     * 已结清融资本息
     */
    private BigDecimal totalPayableAccount;
    /**
     * 未结清融资本息
     */
    private BigDecimal payedAccount;
    /**
     * 本月应还
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
}
