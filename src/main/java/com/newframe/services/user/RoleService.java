package com.newframe.services.user;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.RentMerchantApplyDTO;
import com.newframe.dto.user.request.RoleApplyDTO;
import com.newframe.dto.user.response.UserRoleApplyDTO;
import com.newframe.dto.user.response.UserRoleDTO;
import com.newframe.entity.user.MerchantAppoint;

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
    List<Long> getAppointSupplierUid(Long uid);

    /**
     * 根据供应商id找出供应商信息
     * @param supplierUid
     * @return
     */
    OperationResult<List<UserRoleDTO.Supplier>> getAppointSupplier(List<Long> supplierUid);

    /**
     * 获取所有的供应商
     * @return
     */
    OperationResult<List<UserRoleDTO.Supplier>> getAllSupplier();

    /**
     * 批量添加指定供应商
     * @param uid
     * @param supplierUid
     * @return
     */
    OperationResult<List<MerchantAppoint>> batchInsert(Long uid, Long[] supplierUid);

    /**
     * 根据供应商id找出供应商信息
     * @param uid
     * @param supplierUid
     * @return
     */
    List<MerchantAppoint> getAppointSupplier(Long uid, Long[] supplierUid);

    /**
     * 删除操作
     * @param merchantAppoints
     */
    void removeAppointSupplier(List<MerchantAppoint> merchantAppoints);

    /**
     * 根据uid获取小B列表
     * @param uid
     * @return
     */
    OperationResult<List<UserRoleDTO.SmallRentMechant>> getSmallRentMechantList(Long uid);

    /**
     * 新增小B
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    OperationResult<Boolean> addSmallRentMechant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO);

    /**
     * 修改小B
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    OperationResult<Boolean> modifySmallRentMechant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO);

    /**
     * 删除小B
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    OperationResult<Boolean> removeSmallRentMechant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO);
}