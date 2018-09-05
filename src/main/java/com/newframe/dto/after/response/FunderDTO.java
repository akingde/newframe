package com.newframe.dto.after.response;

import com.newframe.entity.user.UserFunder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WangBin
 */
@Data
public class FunderDTO {

    private Long funderUid;
    private String merchantName;
    private String phoneNumber;
    private String legalEntity;
    private String legalEntityIdNumber;
    private String topContacts;
    private String topContactsPhoneNumber;
    private Integer relationship;
    private Integer ctime;
    private String businessLicenseNumber;
    private String[] businessLicense;
    private String[] letterOfAttorney;
    private String[] businessQualification;

    public FunderDTO(){
    }

    public FunderDTO(UserFunder userFunder) {
        this.funderUid = userFunder.getUid();
        this.merchantName = userFunder.getMerchantName();
        this.phoneNumber = userFunder.getPhoneNumber();
        this.legalEntity = userFunder.getLegalEntity();
        this.legalEntityIdNumber = userFunder.getLegalEntityIdNumber();
        this.topContacts = userFunder.getTopContacts();
        this.topContactsPhoneNumber = userFunder.getTopContactsPhoneNumber();
        this.relationship = userFunder.getRelationship();
        this.ctime = userFunder.getCtime();
        this.businessLicenseNumber = userFunder.getBusinessLicenseNumber();
        this.businessLicense = StringUtils.split(userFunder.getBusinessLicenseFile(),",");
        this.letterOfAttorney = StringUtils.split(userFunder.getLetterOfAttorneyFile(),",");
        this.businessQualification = StringUtils.split(userFunder.getBusinessQualificationFile(),",");
    }
}
