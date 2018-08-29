package com.newframe.dto.after.response;

import com.newframe.entity.user.UserRoleApply;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class RoleApplyListDTO {

    private Long roleApplyId;
    private Integer ctime;
    private Integer roleId;
    private String merchantName;
    private String legalEntity;
    private String legalEntityIdNumber;
    private String phoneNumber;
    private String businessLicenseNumber;
    private Integer roleStatus;

    public RoleApplyListDTO() {
    }

    public RoleApplyListDTO(UserRoleApply userRoleApply) {
        this.roleApplyId = userRoleApply.getId();
        this.ctime = userRoleApply.getCtime();
        this.roleId = userRoleApply.getRoleId();
        this.merchantName = userRoleApply.getMerchantName();
        this.legalEntity = userRoleApply.getLegalEntity();
        this.legalEntityIdNumber = userRoleApply.getLegalEntityIdNumber();
        this.phoneNumber = userRoleApply.getPhoneNumber();
        this.businessLicenseNumber = userRoleApply.getBusinessLicenseNumber();
        this.roleStatus = userRoleApply.getApplyStatus();
    }
}
