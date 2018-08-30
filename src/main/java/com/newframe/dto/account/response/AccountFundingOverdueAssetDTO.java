package com.newframe.dto.account.response;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 资金方逾期资产
 * </p>
 *
 * @author zww
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountFundingOverdueAssetDTO implements Serializable {
    /**
     * 逾期金额合计
     */
    private BigDecimal totalOverdueAccount;
    /**
     * 逾期笔数
     */
    private Integer overdueNumber;
    /**
     * 逾期率
     */
    private Double overdueRate;
}
