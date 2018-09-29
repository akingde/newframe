package com.newframe.dto.merchant.order;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author kfm
 * @date 2018.09.28 11:39
 */
@Data
public class ReletDTO  extends MerchantInfoDTO{
    @NotNull(message = "续租时间不能为空")
    @Min(value = 1,message = "续租时间不能小于1个月")
    @Max(value = 12,message = "续租时间不能超过12个月")
    private Integer reletPeriod;
}
