package com.newframe.services.after.impl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.after.request.FunderSearchDTO;
import com.newframe.dto.after.request.RoleListSearchDTO;
import com.newframe.dto.after.response.*;
import com.newframe.entity.user.UserRoleApply;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.enums.user.RoleStatusEnum;
import com.newframe.services.after.AfterService;
import com.newframe.services.user.RoleBaseService;
import com.newframe.services.userbase.UserRoleApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @author WangBin
 */
@Service
public class AfterServiceImpl implements AfterService {

    @Autowired
    private UserRoleApplyService userRoleApplyService;
    @Autowired
    private RoleBaseService roleBaseService;

    /**
     * 后台登陆
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public OperationResult<UserDTO> login(String userName, String password) {
        String name = "admin", pwd = "nimda";
        if(StringUtils.isEmpty(userName) || !userName.equals(name)){
            return new OperationResult(RequestResultEnum.LOGIN_ERROR);
        }
        if(StringUtils.isEmpty(password) || !password.equals(pwd)){
            return new OperationResult(RequestResultEnum.LOGIN_ERROR);
        }
        return new OperationResult();
    }

    /**
     * 获取角色列表
     *
     * @param condition
     * @return
     */
    @Override
    public OperationResult<RoleListDTO> getMechantList(RoleListSearchDTO condition) {
        Page<UserRoleApply> page = userRoleApplyService.findAll(condition);
        return new OperationResult(new RoleListDTO(page));
    }

    /**
     * 获取角色申请详细信息
     *
     * @param roleApplyId
     * @return
     */
    @Override
    public OperationResult<RoleApplyInfoDTO> getMechantInfo(Long roleApplyId) {
        UserRoleApply roleApply = userRoleApplyService.findOne(roleApplyId, null);
        return new OperationResult(new RoleApplyInfoDTO(roleApply));
    }

    /**
     * 审核通过
     *
     * @param uid
     * @param roleApplyId
     * @return
     */
    @Override
    public OperationResult<Boolean> passCheck(Long uid, Long roleApplyId) {
        UserRoleApply roleApply = userRoleApplyService.findOne(roleApplyId, null);
        if(roleApply == null || !roleApply.getApplyStatus().equals(RoleStatusEnum.UNDER_REVIEW.getRoleStatue())){
            return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
        }
        return roleBaseService.passRoleApply(roleApply);
    }

    /**
     * 审核失败
     *
     * @param uid
     * @param roleApplyId
     * @param remarks
     * @return
     */
    @Override
    public OperationResult<Boolean> failCheck(Long uid, Long roleApplyId, String remarks) {
        UserRoleApply roleApply = userRoleApplyService.findOne(roleApplyId, null);
        if(roleApply == null || !roleApply.getApplyStatus().equals(RoleStatusEnum.UNDER_REVIEW.getRoleStatue())){
            return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
        }
        roleApply.setApplyStatus(RoleStatusEnum.REVIEW_FAILURE.getRoleStatue());
        roleApply.setRemarks(remarks);
        roleApply.setCheckUid(uid);
        roleApply.setCheckPerson(new UserDTO().getUserName());
        userRoleApplyService.updateByRoleApplyId(roleApply);
        return new OperationResult(true);
    }

    /**
     * 获取白名单列表
     *
     * @param condiiton
     * @return
     */
    @Override
    public OperationResult<WhiteFunderListDTO> getWhiteList(FunderSearchDTO condiiton) {
        return null;
    }

    /**
     * 获取非白名单列表
     *
     * @return
     */
    @Override
    public OperationResult<FunderDTO> getBlackFunderList() {
        return null;
    }

    /**
     * 添加资金方
     *
     * @param uid
     * @param funderUids
     * @return
     */
    @Override
    public OperationResult<Boolean> addFunder(Long uid, List<Long> funderUids) {
        return null;
    }

    /**
     * 删除资金方
     *
     * @param uid
     * @param funderUid
     * @return
     */
    @Override
    public OperationResult<Boolean> removeFunder(Long uid, Long funderUid) {
        return null;
    }
}
