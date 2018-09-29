package com.newframe.dto.merchant.order;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.29 11:22
 */
@Data
public class RepayNoticeDTO extends MerchantInfoDTO{
    @NotNull(message = "还款期数不能为空")
    private Integer numberOfPeriods;
    @NotNull(message = "还款金额不能为空")
    private BigDecimal repayAmount;

}
