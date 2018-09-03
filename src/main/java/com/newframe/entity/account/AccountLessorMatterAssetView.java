package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>
 * 出租方实物资产
 * </p>
 *
 * @author zww
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountLessorMatterAssetView implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @Id
    private Long uid;
    /**
     * 投资回报率
     */
    private double InvestReturnRate;
    /**
     * 市场平均投资回报率
     */
    private double AverageInvestReturnRate;
    /**
     * 租赁总额
     */
    private double totalRentAmount;
    /**
     * 累计应付租金
     */
    private double totalPayableAmount;
    /**
     * 已付租金
     */
    private double payedAmount;
    /**
     * 待付租金
     */
    private double unpayAmount;
    /**
     * ctime
     */
    private Long ctime;
    /**
     * utime
     */
    private Long utime;


}
