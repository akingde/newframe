package com.newframe.dto.after.response;

import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class WhiteFunderDTO extends FunderDTO{

    private Integer roleStatus;
    private Long checkPersonUid;
    private String checkPerson;
}
