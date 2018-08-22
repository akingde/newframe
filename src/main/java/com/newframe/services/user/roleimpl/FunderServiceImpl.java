package com.newframe.services.user.roleimpl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.FunderApplyDTO;
import com.newframe.dto.user.request.RoleApplyDTO;
import com.newframe.dto.user.response.UserRoleApplyDTO;
import com.newframe.dto.user.response.UserRoleDTO;
import com.newframe.entity.user.UserFunder;
import com.newframe.entity.user.UserRoleApply;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.user.PatternEnum;
import com.newframe.enums.user.RelationshipEnum;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.services.user.RoleService;
import com.newframe.services.userbase.UserFunderService;
import com.newframe.services.userbase.UserRoleApplyService;
import com.newframe.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author WangBin
 */
@Service
public class FunderServiceImpl implements RoleService {

    @Autowired
    private UserRoleApplyService userRoleApplyService;
    @Autowired
    private UserFunderService userFunderService;


    @Override
    public Integer getRoleId() {
        return RoleEnum.FUNDER.getRoleId();
    }

    /**
     * 角色申请
     *
     * @param uid
     * @param roleApplyDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> roleApply(Long uid, RoleApplyDTO roleApplyDTO) {
        FunderApplyDTO roleApply = (FunderApplyDTO)roleApplyDTO;
        if(StringUtils.isAnyEmpty(roleApply.getTopContacts()) || RelationshipEnum.isEmpty(roleApply.getRelationship())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if (PatternEnum.checkPattern(roleApply.getTopContactsPhoneNumber(), PatternEnum.mobile)){
            return new OperationResult(RequestResultEnum.MOBILE_INVALID, false);
        }
        if(roleApply.getBusinessQualification() == null || roleApply.getBusinessQualification().length > 2){
            return new OperationResult(RequestResultEnum.FILE_TOO_MUCH, false);
        }
        if(roleApply.getLetterOfAttorney() == null || roleApply.getLetterOfAttorney().length > 2){
            return new OperationResult(RequestResultEnum.FILE_TOO_MUCH, false);
        }
        return null;
    }

    /**
     * 获取角色申请详细信息
     *
     * @param uid
     * @param roleApplyId
     * @return
     */
    @Override
    public OperationResult<UserRoleApplyDTO> getUserRoleApplyInfo(Long uid, Long roleApplyId) {
        UserRoleApply roleApply = userRoleApplyService.findOne(roleApplyId, uid);
        return new OperationResult(roleApply == null ? new UserRoleDTO() : new UserRoleApplyDTO.Funder(roleApply));
    }

    /**
     * 根据uid获取角色信息
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<UserRoleDTO> getUserRoleInfo(Long uid) {
        UserFunder funder = userFunderService.findOne(uid);
        return new OperationResult(funder == null ? new UserRoleDTO() : new UserRoleDTO.Funder(funder));
    }

    /**
     * 设置指定供应商开关
     *
     * @param uid
     * @param appoint
     * @return
     */
    @Override
    public OperationResult<Boolean> setAppoint(Long uid, boolean appoint) {
        return new OperationResult(RequestResultEnum.INVALID_ACCESS, false);
    }

    /**
     * 获取指定的供应商
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<List<UserRoleDTO.Supplier>> getAppointSupplier(Long uid) {
        return new OperationResult(Collections.emptyList());
    }

    /**
     * 获取所有的供应商
     *
     * @return
     */
    @Override
    public OperationResult<List<UserRoleDTO.Supplier>> getAllSupplier() {
        return new OperationResult(Collections.emptyList());
    }
}
