package com.newframe.entity.order;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author kfm
 * 融资购机表
 * 租赁商进行融资购机是，
 */
@Entity
@Data
@Table(name = "financing_buy_machine")
public class FinancingBuyMachine {
    @Column(name = "order_id")
    @Id
    private Long orderId;

    @Column(name = "merchant_id")
    private Long merchantId;

    @Column(name = "merchant_name")
    private String merchantName;

    @Column(name = "funder_id")
    private Long funderId;

    @Column(name = "funder_name")
    private String funderName;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "user_realname")
    private String userRealname;

    @Column(name = "user_id_number")
    private String userIdNumber;

    @Column(name = "user_credit")
    private Integer userCredit;

    @Column(name = "financing_amount")
    private BigDecimal financingAmount;

    @Column(name = "financing_deadline")
    private BigDecimal financingDeadline;

    @Column(name = "supply_id")
    private Long supplyId;

    @Column(name = "supply_name")
    private String supplyName;

    @Column(name = "dispatch_times")
    private Integer dispatchTimes;

    @Column(name = "financing_status")
    private Integer financingStatus;

    @Column(name = "delete_status")
    private Integer deleteStatus;

    @Column(name = "ctime")
    private Integer ctime;

    @Column(name = "utime")
    private Integer utime;

}
