package com.newframe.dto.user.request;

import lombok.Data;

/**
 * 地址请求类
 * @author WangBin
 */
@Data
public class AddressDTO {

    /**
     *  地址id
     */
    private Long addressId;
    /**
     *  默认地址
     */
    private boolean defaultAddress;
    /**
     *  接收人姓名
     */
    private String consigneeName;
    /**
     *  省id
     */
    private Integer provinceId;
    /**
     *  市id
     */
    private Integer cityId;
    /**
     *  区id
     */
    private Integer countyId;
    /**
     *  详细地址
     */
    private String consigneeAddress;
    /**
     *  手机号
     */
    private String mobile;
}
