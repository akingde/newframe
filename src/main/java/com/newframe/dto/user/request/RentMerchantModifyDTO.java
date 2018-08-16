package com.newframe.dto.user.request;

import lombok.Data;

/**
 *  租赁商信息修改
 * @author WangBin
 */
@Data
public class RentMerchantModifyDTO extends RoleModifyDTO{
    /**
     *  修改的小B的uid
     */
    private Long modifyUid;
    /**
     * 手机号(小B专属)
     */
    private String phoneNumber;
    /**
     *  营业执照
     */
    private String[] businessListen;
    /**
     *  最高学历证书
     */
    private String[] highestDegreeDiploma;
    /**
     *  行驶证
     */
    private String[] drivingLicense;
    /**
     *  房产证
     */
    private String[] houseProprietaryCertificate;
}
