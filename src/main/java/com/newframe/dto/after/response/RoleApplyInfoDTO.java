package com.newframe.dto.after.response;

import com.newframe.entity.user.UserRoleApply;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WangBin
 */
@Data
public class RoleApplyInfoDTO extends RoleApplyListDTO{

    private String legalEntityIdCardFront; //身份证正面
    private String legalEntityIdCardBack; // 身份证背面
    private String contactsPhoneNumber;//联系人手机号
    private String job;//职位
    private String topContacts;
    private String topContactsPhoneNumber;
    private Integer relationship;
    private String[] businessLicense;
    private String[] letterOfAttorney;
    private String[] businessQualification;
    private String[] highestDegreeDiploma;
    private String[] drivingLicense;
    private String[] houseProprietaryCertificate;

    public RoleApplyInfoDTO() {
    }

    public RoleApplyInfoDTO(UserRoleApply userRoleApply) {
        super(userRoleApply);
        this.legalEntityIdCardFront = userRoleApply.getIdCardFrontFile();
        this.legalEntityIdCardBack = userRoleApply.getIdCardBackFile();
        this.contactsPhoneNumber = userRoleApply.getContactsPhoneNumber();
        this.job = userRoleApply.getJob();
        this.topContacts = userRoleApply.getTopContacts();
        this.topContactsPhoneNumber = userRoleApply.getTopContactsPhoneNumber();
        this.relationship = userRoleApply.getRelationship();
        this.businessLicense = StringUtils.split(userRoleApply.getBusinessLicenseFile(),",");
        this.letterOfAttorney = StringUtils.split(userRoleApply.getBusinessLicenseFile(), ",");
        this.businessQualification = StringUtils.split(userRoleApply.getBusinessQualificationFile(),",");
        this.highestDegreeDiploma = StringUtils.split(userRoleApply.getHighestDegreeDiplomaFile(),",");
        this.drivingLicense = StringUtils.split(userRoleApply.getDrivingLicenseFile(), ",");
        this.houseProprietaryCertificate = StringUtils.split(userRoleApply.getHouseProprietaryCertificateFile(), ",");
    }
}
