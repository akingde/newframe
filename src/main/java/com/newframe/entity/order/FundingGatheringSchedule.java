package com.newframe.entity.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.13 11:06
 */
@Data
@Entity
public class FundingGatheringSchedule {
    @Id
    private Long id;
    private Long uid;
    private Long orderId;
    private Long renterId;
    private BigDecimal repayAmount;
    private BigDecimal interest;
    private BigDecimal totalAmount;
    private Integer lastRepayTime;
    private Integer actualRepayTime;
    private Integer repayStatus;
    private Integer ctime;
    private Integer utime;
    private Integer numberPeriods;
}
