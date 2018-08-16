package com.newframe.dto.user.request;

import lombok.Data;

/**
 *  资金方信息修改申请表
 * @author WangBin
 */
@Data
public class FunderModifyDTO extends RoleModifyDTO{

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
    /**
     *  委托授权书  资金方
     */
    private String[] letterOfAttorney;
    /**
     *  经营资质    资金方
     */
    private String[] businessQualification;
}
