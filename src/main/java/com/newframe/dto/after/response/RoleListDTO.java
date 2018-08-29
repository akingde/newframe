package com.newframe.dto.after.response;

import lombok.Data;

import java.util.List;

/**
 * @author WangBin
 */
@Data
public class RoleListDTO {

    private Long total;
    private List<RoleApplyListDTO> result;
}
