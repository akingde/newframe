package com.newframe.dto.user.response;

import lombok.Data;

/**
 *  小B分页查询
 * @author WangBin
 */
@Data
public class SecondRentMerchantDTO {

    private Long total;
    private SecondRentMerchantResult result;

    @Data
    public static class SecondRentMerchantResult{
        /**
         * 小Buid
         */
        private Long sonUid;
        /**
         * 角色id
         */
        private Integer roleId;
        /**
         * 商家名称
         */
        private String merchantName;
        /**
         *  法人
         */
        private String legalEntity;
        /**
         *  法人姓名
         */
        private String legalEntityIdNumber;
        /**
         *  商家手机号
         */
        private String merchantPhone;
        /**
         *  营业执照编号
         */
        private String businessLicenseNumber;
        /**
         *  营业执照
         */
        private String businessListen;
        /**
         *  接收地址
         */
        private String rentMerchantAddress;
        /**
         *  最高学历证明
         */
        private String highestDegreeDiploma;
        /**
         *  行驶证
         */
        private String drivingLicense;
        /**
         *  房产证
         */
        private String houseProprietaryCertificate;
        /**
         *  状态
         */
        private Integer status;
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
    }
}
