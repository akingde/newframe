package com.newframe.dto.user.request;

import lombok.Data;

/**
 *  出租方申请类
 * @author WangBin
 */
@Data
public class HirerApplyDTO extends RoleApplyDTO {

    /**
     *  紧急联系人
     */
    private String topContacts;
    /**
     *  紧急联系人手机号
     */
    private String topContactsPhoneNumber;
    /**
     *  关系
     */
    private Integer relationship;
}
