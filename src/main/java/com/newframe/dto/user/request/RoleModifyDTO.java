package com.newframe.dto.user.request;

import lombok.Data;

/**
 * 修改角色信息的公共属性
 * @author WangBin
 */
@Data
public class RoleModifyDTO{
    /**
     *  角色申请id  通用(小B除外)
     */
    private Long roleApplyId;
    /**
     * 商家名称
     */
    private String name;
    /**
     *  法人
     */
    private String legalEntity;
    /**
     *  法人身份证号
     */
    private String legalEntityIdNumber;
    /**
     *  营业执照编号
     */
    private String businessListenNumber;
    /**
     *  营业执照
     */
    private String[] businessListen;
}
