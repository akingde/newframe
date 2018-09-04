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
        this.businessQualification = StringUtils.isNotEmpty(userRoleApply.getBusinessQualificationFile())?
                userRoleApply.getBusinessQualificationFile().split(","):
                null;
        this.highestDegreeDiploma = StringUtils.isNotEmpty(userRoleApply.getHighestDegreeDiplomaFile())?
                userRoleApply.getHighestDegreeDiplomaFile().split(","):
                null;
        this.drivingLicense = StringUtils.isNotEmpty(userRoleApply.getDrivingLicenseFile())?
                userRoleApply.getDrivingLicenseFile().split(","):
                null;
        this.houseProprietaryCertificate = StringUtils.isNotEmpty(userRoleApply.getHouseProprietaryCertificateFile())?
                userRoleApply.getHouseProprietaryCertificateFile().split(","):
                null;
    }
}
