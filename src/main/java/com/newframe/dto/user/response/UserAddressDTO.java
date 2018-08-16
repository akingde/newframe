package com.newframe.dto.user.response;

import lombok.Data;

/**
 *  用户地址表
 * @author WangBin
 */
@Data
public class UserAddressDTO {

    private Long total;
    private UserAddressResult result;

    @Data
    public static class UserAddressResult{
        /**
         * 用户id
         */
        private Long uid;
        /**
         * 地址id
         */
        private Long addressId;
        /**
         * 省id
         */
        private Integer provinceId;
        /**
         * 省名
         */
        private String provinceName;
        /**
         * 市id
         */
        private Integer cityId;
        /**
         * 市名
         */
        private String cityName;
        /**
         * 区id
         */
        private Integer countyId;
        /**
         * 区名
         */
        private String counttyName;
        /**
         * 详细地址
         */
        private String consigneeAddress;
        /**
         * 默认地址
         */
        private Boolean defaultAddress;
    }
}
