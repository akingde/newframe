package com.newframe.dto.user.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *  角色申请
 * @author WangBin
 */
@Data
public class RoleApplyDTO {
    /**
     * 商家名称
     */
    private String name;
    /**
     * 法人
     */
    private String legalEntity;
    /**
     * 法人身份证号
     */
    private String legalEntityIdNumber;
    /**
     * 营业执照编号
     */
    private String businessListenNumber;
    /**
     * 身份证正面
     */
    private MultipartFile legalEntityIdCardFront;
    /**
     * 身份证背面
     */
    private MultipartFile legalEntityIdCardBack;
    /**
     *  营业执照
     */
    private List<MultipartFile> businessListen;
    /**
     * 联系人手机号
     */
    private String contactsPhoneNumber;
    /**
     * 职位
     */
    private String job;
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
