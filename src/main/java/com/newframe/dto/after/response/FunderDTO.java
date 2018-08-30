package com.newframe.dto.after.response;

import com.newframe.entity.user.UserFunder;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class FunderDTO {

    private Long funderUid;
    private String merchantName;
    private String legalEntity;
    private String legalEntityIdNumber;
    private String topContacts;
    private String topContactsPhoneNumber;
    private Integer relationship;
    private Long ctime;
    private String businessLicenseNumber;
    private String[] businessLicense;
    private String[] letterOfAttorney;
    private String[] businessQualification;

    public FunderDTO(){
    }

    public FunderDTO(UserFunder userFunder) {
        this.funderUid = userFunder.getUid();
        this.merchantName = userFunder.getMerchantName();
        this.legalEntity = userFunder.getLegalEntity();
        this.legalEntityIdNumber = userFunder.getLegalEntityIdNumber();
        this.topContacts = userFunder.getTopContacts();
        this.topContactsPhoneNumber = userFunder.getTopContactsPhoneNumber();
        this.relationship = userFunder.getRelationship();
        this.ctime = userFunder.getUid();
        this.businessLicenseNumber = userFunder.getBusinessLicenseNumber();
        this.businessLicense = userFunder.getBusinessLicenseFile().split(",");
        this.letterOfAttorney = userFunder.getLetterOfAttorneyFile().split(",");
        this.businessQualification = userFunder.getBusinessQualificationFile().split(",");
    }
}
