package com.newframe.dto.user.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *  资金方信息申请表
 * @author WangBin
 */
@Data
public class FunderApplyDTO extends RoleApplyDTO {

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
    private List<MultipartFile> letterOfAttorney;
    /**
     *  经营资质    资金方
     */
    private List<MultipartFile> businessQualification;
}
