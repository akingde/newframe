package com.newframe.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 资金方金融资产表
 * </p>
 *
 * @author zww
 * @since 2018-08-29
 */
@Data
public class AccountFundingFinanceAssetDTO {
    /**
     * 投资回报率
     */
    private BigDecimal investReturnRate;
    /**
     * 市场平均投资回报率
     */
    private BigDecimal averageInvestReturnRate;
}
