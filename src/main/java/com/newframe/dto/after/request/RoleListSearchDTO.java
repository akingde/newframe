package com.newframe.dto.after.request;

import com.newframe.dto.user.request.PageSearchDTO;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class RoleListSearchDTO extends PageSearchDTO {

    private Integer roleType;
    private String mechantName;
    private String legalEntity;
    private String phoneNumber;
    private Integer roleStatus;
    private Integer startTime;
    private Integer endTime;
}
