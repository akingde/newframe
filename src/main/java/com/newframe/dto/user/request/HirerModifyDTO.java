package com.newframe.dto.user.request;

import lombok.Data;

/**
 *  祝租房信息修改类
 * @author WangBin
 */
@Data
public class HirerModifyDTO extends RoleModifyDTO {

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
