package com.newframe.dto.user.response;

import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class UserRoleDTO{

    private Long uid;
    private Integer roleId;
    private String name;//商家名称
    private String legalEntity;//法人
    private String legalEntityIdNuber;//法人身份证号
    private String businessListenNumber;//营业执照
    private String businessListen;//营业执照文件url

    /**
     * 租赁商
     */
    @Data
    public static class RentMechant extends UserRoleDTO {
        private String highestDegreeDiploma;//最高学历
        private String drivingLicense;//行驶证
        private String houseProprietaryCertificate;//房产证
        private Boolean appoint;    //是否指定供应商
    }

    /**
     * 资金方
     */
    @Data
    public static class Funder extends UserRoleDTO {
        private String topContacts;//紧急联系人
        private String topContactsPhoneNumber;//基金联系人手机号
        private Integer relationship;//关系
        private String letterOfAttorney;//委托授权书
        private String businessQualification;//经营资质
    }

    /**
     * 出租方
     */
    @Data
    public static class Hirer extends UserRoleDTO {
        private String topContacts;//紧急联系人
        private String topContactsPhoneNumber;//基金联系人手机号
        private Integer relationship;//关系
    }

    /**
     * 供应商
     */
    @Data
    public static class Supplier extends UserRoleDTO {
    }
}
