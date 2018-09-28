package com.newframe.dto.merchant.order;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author kfm
 * @date 2018.09.28 11:39
 */
@Data
public class MerchantInfoDTO {
    @NotNull(message = "平台id不能为空")
    private Long partnerId;
    @NotBlank(message = "商家平台订单id不能为空")
    private String partnerOrderId;
}
