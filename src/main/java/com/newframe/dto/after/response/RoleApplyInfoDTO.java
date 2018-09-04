package com.newframe.dto.after.response;

import com.newframe.entity.user.UserRoleApply;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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
        this.businessLicense = StringUtils.isNotEmpty(userRoleApply.getBusinessLicenseFile())?
                userRoleApply.getBusinessLicenseFile().split(",") :
                null;
        this.letterOfAttorney = StringUtils.isNotEmpty(userRoleApply.getBusinessLicenseFile())?
                userRoleApply.getLetterOfAttorneyFile().split(","):
                null;
        this.businessQualification = StringUtils.split(userRoleApply.getBusinessQualificationFile(),",");
        this.highestDegreeDiploma = StringUtils.split(userRoleApply.getHighestDegreeDiplomaFile(),",");
        this.drivingLicense = StringUtils.split(userRoleApply.getDrivingLicenseFile(), ",");
        this.houseProprietaryCertificate = StringUtils.split(userRoleApply.getHouseProprietaryCertificateFile(), ",");
    }
}
