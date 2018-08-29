package com.newframe.dto.user.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author WangBin
 */
@Data
public class RentMerchantApplyDTO extends RoleApplyDTO{
    /**
     *  修改的小B的uid
     */
    private Long modifyUid;
    /**
     * 手机号(小B专属)
     */
    private String merchantPhone;
    /**
     *  最高学历证书
     */
    private List<MultipartFile> highestDegreeDiploma;
    /**
     *  行驶证
     */
    private List<MultipartFile> drivingLicense;
    /**
     *  房产证
     */
    private List<MultipartFile> houseProprietaryCertificate;
    /**
     * 省id
     */
    private Integer provinceId;
    /**
     * 市id
     */
    private Integer cityId;
    /**
     * 县id
     */
    private Integer countyId;
    /**
     * 详细地址
     */
    private String consigneeAddress;
}
