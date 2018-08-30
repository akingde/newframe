package com.newframe.dto.user.response;

import com.newframe.entity.user.UserRoleApply;
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

    public UserRoleApplyDTO() {
    }

    public UserRoleApplyDTO(UserRoleApply userRoleApply) {
        this.roleAppleId = userRoleApply.getId();
        this.roleId = userRoleApply.getRoleId();
    }

    /**
     * 角色申请信息
     */
    @Data
    public static class RoleApplyResult extends UserRoleApplyDTO{
        private Integer status;//状态
        private Integer ctime;//申请时间
        private String remarks;//备注

        public RoleApplyResult(UserRoleApply userRoleApply) {
            super(userRoleApply);
            this.status = userRoleApply.getApplyStatus();
            this.ctime = userRoleApply.getCtime();
            this.remarks = userRoleApply.getRemarks();
        }
    }

    /**
     * 角色申请详细信息
     */
    @Data
    public static class RoleApplyInfo extends UserRoleApplyDTO{
        private Long uid;//用户id
        private String name;//商家名称
        private String legalEntity;//法人
        private String legalEntityIdNumber;//法人身份证号
        private String businessListenNumber;//营业执照
        private String[] businessListen;//营业执照文件url

        public RoleApplyInfo(UserRoleApply userRoleApply) {
            super(userRoleApply);
            this.uid = userRoleApply.getUid();
            this.name = userRoleApply.getMerchantName();
            this.legalEntity = userRoleApply.getLegalEntity();
            this.legalEntityIdNumber = userRoleApply.getLegalEntityIdNumber();
            this.businessListenNumber = userRoleApply.getBusinessLicenseNumber();
            this.businessListen = userRoleApply.getBusinessLicenseFile().split(",");
        }
    }

    /**
     * 租赁商
     */
    @Data
    public static class RentMechant extends RoleApplyInfo {
        private String[] highestDegreeDiploma;//最高学历
        private String[] drivingLicense;//行驶证
        private String[] houseProprietaryCertificate;//房产证

        public RentMechant(UserRoleApply userRoleApply) {
            super(userRoleApply);
            this.highestDegreeDiploma = userRoleApply.getHighestDegreeDiplomaFile().split(",");
            this.drivingLicense = userRoleApply.getDrivingLicenseFile().split(",");
            this.houseProprietaryCertificate = userRoleApply.getHouseProprietaryCertificateFile().split(",");
        }
    }

    /**
     * 资金方
     */
    @Data
    public static class Funder extends RoleApplyInfo {
        private String topContacts;//紧急联系人
        private String topContactsPhoneNumber;//紧急联系人手机号
        private Integer relationship;//关系
        private String[] letterOfAttorney;//委托授权书
        private String[] businessQualification;//经营资质

        public Funder(UserRoleApply userRoleApply) {
            super(userRoleApply);
            this.topContacts = userRoleApply.getTopContacts();
            this.topContactsPhoneNumber = userRoleApply.getTopContactsPhoneNumber();
            this.relationship = userRoleApply.getRelationship();
            this.letterOfAttorney = userRoleApply.getLetterOfAttorneyFile().split(",");
            this.businessQualification = userRoleApply.getBusinessQualificationFile().split(",");
        }
    }

    /**
     * 出租方
     */
    @Data
    public static class Hirer extends RoleApplyInfo {
        private String topContacts;//紧急联系人
        private String topContactsPhoneNumber;//基金联系人手机号
        private Integer relationship;//关系

        public Hirer(UserRoleApply userRoleApply) {
            super(userRoleApply);
            this.topContacts = userRoleApply.getTopContacts();
            this.topContactsPhoneNumber = userRoleApply.getTopContactsPhoneNumber();
            this.relationship = userRoleApply.getRelationship();
        }
    }

    /**
     * 供应商
     */
    @Data
    public static class Supplier extends RoleApplyInfo {
        public Supplier(UserRoleApply userRoleApply) {
            super(userRoleApply);
        }
    }
}