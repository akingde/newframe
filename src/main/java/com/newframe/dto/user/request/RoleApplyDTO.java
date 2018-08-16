package com.newframe.dto.user.request;

import lombok.Data;

/**
 *  角色申请
 * @author WangBin
 */
@Data
public class RoleApplyDTO {
    private String name;                    //商家名称
    private String legalEntity;             //法人
    private String legalEntityIdNumber;     //法人身份证号
    private String businessListenNumber;    //营业执照编号
    private String topContacts;              //紧急联系人
    private String topContactsPhoneNumber;  //紧急联系人手机号
    private Integer relationship;           //关系
}
//    public String getName() {
//        return name == null || name.length() == 0 ? null : name;
//    }
//
//    public String getLegalEntity() {
//        return legalEntity == null || legalEntity.length() == 0 ? null : legalEntity;
//    }
//
//    public String checkLegalEntityIdNumber() {
//        return IdNumberUtils.isValidatedAllIdcard(legalEntityIdNumber) ? legalEntityIdNumber : null;
//    }
//
//    public String checkBusinessListenNumber() {
//        return businessListenNumber == null ? null : businessListenNumber;
//    }
//
//    public MultipartFile[] checkBusinessListen() {
//        if(businessListen == null || businessListen.length > 2)
//            return null;
//        return FileUtils.checkImage(businessListen) ? businessListen : null;
//    }
