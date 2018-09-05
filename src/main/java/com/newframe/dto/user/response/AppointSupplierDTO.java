package com.newframe.dto.user.response;

import com.newframe.entity.user.UserSupplier;
import com.newframe.enums.RoleEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author WangBin
 */
@Data
public class AppointSupplierDTO {

    private boolean appoint;
    private List<UserRoleDTO.Supplier> result;

    public AppointSupplierDTO(boolean appoint) {
        this.appoint = appoint;
    }

    public AppointSupplierDTO(boolean appoint, List<UserRoleDTO.Supplier> result) {
        this.appoint = appoint;
        this.result = result;
    }
}
