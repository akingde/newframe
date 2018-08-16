package com.newframe.dto.user.response;

import lombok.Data;

import java.util.List;

/**
 * 角色查询返回类
 * @author WangBin
 */
@Data
public class UserRoleApplyDTO {

    private Long roleAppleId;//角色申请id
    private Integer roleId; //角色id

    /**
     * 角色申请列表
     */
    @Data
    public static class RoleApplyList{
        private Long total;
        private List<RoleApplyResult> result;
    }

    /**
     * 角色申请信息
     */
    @Data
    public static class RoleApplyResult extends UserRoleApplyDTO{
        private Integer status;//状态
        private Integer ctime;//申请时间
        private String remarks;//备注
    }

    /**
     * 角色申请详细信息
     */
    @Data
    public static class RoleApplyInfo extends UserRoleApplyDTO{
        private Long uid;//用户id
        private String name;//商家名称
        private String legalEntity;//法人
        private String legalEntityIdNuber;//法人身份证号
        private String businessListenNumber;//营业执照
        private String businessListen;//营业执照文件url
    }

    /**
     * 租赁商
     */
    @Data
    public static class RentMechant extends RoleApplyInfo {
        private String highestDegreeDiploma;//最高学历
        private String drivingLicense;//行驶证
        private String houseProprietaryCertificate;//房产证
    }

    /**
     * 资金方
     */
    @Data
    public static class Funder extends RoleApplyInfo {
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
    public static class Hirer extends RoleApplyInfo {
        private String topContacts;//紧急联系人
        private String topContactsPhoneNumber;//基金联系人手机号
        private Integer relationship;//关系
    }

    /**
     * 供应商
     */
    @Data
    public static class Supplier extends RoleApplyInfo {
    }
}