package com.newframe.dto.user.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    private String phoneNumber;
    /**
     *  最高学历证书
     */
    private MultipartFile[] highestDegreeDiploma;
    /**
     *  行驶证
     */
    private MultipartFile[] drivingLicense;
    /**
     *  房产证
     */
    private MultipartFile[] houseProprietaryCertificate;
}
