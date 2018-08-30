package com.newframe.dto.after.response;

import com.newframe.entity.user.UserFunder;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class WhiteFunderDTO extends FunderDTO{

    private Integer roleStatus;
    private Long checkPersonUid;
    private String checkPerson;

    public WhiteFunderDTO(UserFunder userFunder) {
        super(userFunder);
        this.roleStatus = userFunder.getRoleStatus();
        this.checkPersonUid = userFunder.getCheckUid();
        this.checkPerson = userFunder.getCheckPerson();
    }
}
