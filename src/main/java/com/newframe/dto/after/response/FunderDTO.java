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
    private String legalEntityIdCardFront; //身份证正面
    private String legalEntityIdCardBack; // 身份证背面
    private String contactsPhoneNumber;//联系人手机号
    private String job;//职位
    private String topContacts;//紧急联系人
    private String topContactsPhoneNumber;//紧急联系人手机号
    private Integer relationship;//关系
    private Integer ctime;
    private Integer utime;
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
        this.legalEntityIdCardFront = userFunder.getIdCardFrontFile();
        this.legalEntityIdCardBack = userFunder.getIdCardBackFile();
        this.contactsPhoneNumber = userFunder.getContactsPhoneNumber();
        this.job = userFunder.getJob();
        this.topContacts = userFunder.getTopContacts();
        this.topContactsPhoneNumber = userFunder.getTopContactsPhoneNumber();
        this.relationship = userFunder.getRelationship();
        this.ctime = userFunder.getCtime();
        this.utime = userFunder.getUtime();
        this.businessLicenseNumber = userFunder.getBusinessLicenseNumber();
        this.businessLicense = StringUtils.split(userFunder.getBusinessLicenseFile(),",");
        this.letterOfAttorney = StringUtils.split(userFunder.getLetterOfAttorneyFile(),",");
        this.businessQualification = StringUtils.split(userFunder.getBusinessQualificationFile(),",");
    }
}
