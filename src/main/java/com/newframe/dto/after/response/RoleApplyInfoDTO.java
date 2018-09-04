package com.newframe.dto.after.response;

import com.newframe.entity.user.UserRoleApply;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class RoleApplyInfoDTO extends RoleApplyListDTO{

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
        this.topContacts = userRoleApply.getTopContacts();
        this.topContactsPhoneNumber = userRoleApply.getTopContactsPhoneNumber();
        this.relationship = userRoleApply.getRelationship();
        this.businessLicense = userRoleApply.getBusinessLicenseFile().split(",");
        this.letterOfAttorney = userRoleApply.getLetterOfAttorneyFile().split(",");
        this.businessQualification = userRoleApply.getBusinessQualificationFile().split(",");
        this.highestDegreeDiploma = userRoleApply.getHighestDegreeDiplomaFile().split(",");
        this.drivingLicense = userRoleApply.getDrivingLicenseFile().split(",");
        this.houseProprietaryCertificate = userRoleApply.getHouseProprietaryCertificateFile().split(",");
    }


}
