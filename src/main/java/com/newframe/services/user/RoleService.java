package com.newframe.services.user;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.RoleApplyDTO;
import com.newframe.dto.user.response.UserRoleApplyDTO;
import com.newframe.dto.user.response.UserRoleDTO;

import java.util.List;

/**
 * @author WangBin
 */
public interface RoleService {

    Integer getRoleId();

    /**
     * 角色申请
     * @param uid
     * @param roleApplyDTO
     * @return
     */
    OperationResult<Boolean> roleApply(Long uid, RoleApplyDTO roleApplyDTO);

    /**
     * 获取角色申请详细信息
     * @param uid
     * @param roleApplyId
     * @return
     */
    OperationResult<UserRoleApplyDTO> getUserRoleApplyInfo(Long uid, Long roleApplyId);

    /**
     * 根据uid获取角色信息
     * @param uid
     * @return
     */
    OperationResult<UserRoleDTO> getUserRoleInfo(Long uid);

    /**
     * 设置指定供应商开关
     * @param uid
     * @param appoint
     * @return
     */
    OperationResult<Boolean> setAppoint(Long uid, boolean appoint);

    /**
     * 获取指定的供应商
     * @param uid
     * @return
     */
    OperationResult<List<UserRoleDTO.Supplier>> getAppointSupplier(Long uid);

    /**
     * 获取所有的供应商
     * @return
     */
    OperationResult<List<UserRoleDTO.Supplier>> getAllSupplier();
}
