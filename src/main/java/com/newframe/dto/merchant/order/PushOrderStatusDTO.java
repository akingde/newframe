package com.newframe.dto.merchant.order;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author kfm
 * @date 2018.10.02 10:41
 */
@Data
public class PushOrderStatusDTO extends MerchantInfoDTO {
    @NotBlank(message = "订单状态含义不能为空")
    private String value;
    @NotBlank(message = "订单状态不能为空")
    private String orderStatus;
}
