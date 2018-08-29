package com.newframe.dto.after.response;

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
}
