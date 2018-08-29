package com.newframe.dto.after.response;

import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class RoleApplyListDTO {

    private Long roleApplyId;
    private Long ctime;
    private Integer roleId;
    private String mechantName;
    private String legalEntity;
    private String legalEntityIdNumber;
    private String phoneNumber;
    private String businessLicenseNumber;
    private Integer roleStatus;

    public RoleApplyListDTO() {
    }

//    public RoleApplyListDTO() {
//    }
}
