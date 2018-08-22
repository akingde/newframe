package com.newframe.dto.user.response;

import com.newframe.entity.user.UserAddress;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  用户地址表
 * @author WangBin
 */
@Data
public class UserAddressDTO {

    private Long total;
    private List<UserAddressResult> result;

    public UserAddressDTO() {
    }

    public UserAddressDTO(Page<UserAddress> page){
        if (page == null){
            this.total = 0L;
            this.result = Collections.emptyList();
        }else{
            this.total = page.getTotalElements();
            List<UserAddress> content = page.getContent();
            List<UserAddressResult> result = new ArrayList();
            for (UserAddress userAddress : content) {
                result.add(new UserAddressResult(userAddress));
            }
            this.result = result;
        }
    }

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
         * 接收人姓名
         */
        private String consigneeName;
        /**
         * 接收人手机号
         */
        private String mobile;
        /**
         * 默认地址
         */
        private Boolean defaultAddress;

        public UserAddressResult() {
        }

        public UserAddressResult(UserAddress userAddress) {
            this.uid = userAddress.getUid();
            this.addressId = userAddress.getId();
            this.provinceId = userAddress.getProvinceId();
            this.provinceName = userAddress.getProvinceName();
            this.cityId = userAddress.getCityId();
            this.cityName = userAddress.getCityName();
            this.countyId = userAddress.getCountyId();
            this.counttyName = userAddress.getCountyName();
            this.consigneeAddress = userAddress.getConsigneeAddress();
            this.consigneeName = userAddress.getConsigneeName();
            this.mobile = userAddress.getMobile();
            this.defaultAddress = userAddress.getDefaultAddress();
        }
    }
}
