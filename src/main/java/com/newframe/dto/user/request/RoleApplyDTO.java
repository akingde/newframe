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
     *  营业执照
     */
    private List<MultipartFile> businessListen;
}
