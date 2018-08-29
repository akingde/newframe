package com.newframe.dto.after.response;

import com.newframe.entity.user.UserRoleApply;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class RoleApplyInfoDTO extends RoleApplyListDTO{

    private String topContacts;
    private String topcontactsPhoneNumber;
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

    }
}
