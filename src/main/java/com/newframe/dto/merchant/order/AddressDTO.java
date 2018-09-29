package com.newframe.dto.merchant.order;

import lombok.Data;

/**
 * @author kfm
 * @date 2018.09.29 14:29
 */
@Data
public class AddressDTO {
    private Integer addressType;
    private String address;
    private String receiverName;
    private String receiverPhone;
}
