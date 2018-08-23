package com.newframe.services.user.roleimpl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.RentMerchantApplyDTO;
import com.newframe.dto.user.request.RoleApplyDTO;
import com.newframe.dto.user.response.UserRoleApplyDTO;
import com.newframe.dto.user.response.UserRoleDTO;
import com.newframe.entity.user.MerchantAppoint;
import com.newframe.entity.user.UserHirer;
import com.newframe.entity.user.UserRoleApply;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.services.user.RoleService;
import com.newframe.services.userbase.UserHirerService;
import com.newframe.services.userbase.UserRoleApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author WangBin
 */
@Service
public class HirerServiceImpl implements RoleService {

    @Autowired
    private UserRoleApplyService userRoleApplyService;
    @Autowired
    private UserHirerService userHirerService;


    @Override
    public Integer getRoleId() {
        return RoleEnum.HIRER.getRoleId();
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
        return new OperationResult(roleApply == null ? new UserRoleDTO() : new UserRoleApplyDTO.Hirer(roleApply));
    }

    /**
     * 根据uid获取角色信息
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<UserRoleDTO> getUserRoleInfo(Long uid) {
        UserHirer hirer = userHirerService.findOne(uid);
        return new OperationResult(hirer == null ? new UserRoleDTO() : new UserRoleDTO.Hirer(hirer));
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
    public List<Long> getAppointSupplierUid(Long uid) {
        return Collections.emptyList();
    }

    /**
     * 根据供应商id找出供应商信息
     *
     * @param supplierUid
     * @return
     */
    @Override
    public OperationResult<List<UserRoleDTO.Supplier>> getAppointSupplier(List<Long> supplierUid) {
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

    /**
     * 批量添加指定供应商
     *
     * @param uid
     * @param supplierUid
     * @return
     */
    @Override
    public OperationResult<List<MerchantAppoint>> batchInsert(Long uid, Long[] supplierUid) {
        return null;
    }

    /**
     * 根据供应商id找出供应商信息
     *
     * @param uid
     * @param supplierUid
     * @return
     */
    @Override
    public List<MerchantAppoint> getAppointSupplier(Long uid, Long[] supplierUid) {
        return Collections.emptyList();
    }

    /**
     * 删除操作
     *
     * @param merchantAppoints
     */
    @Override
    public void removeAppointSupplier(List<MerchantAppoint> merchantAppoints) {}

    /**
     * 根据uid获取小B列表
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<List<UserRoleDTO.SmallRentMechant>> getSmallRentMechantList(Long uid) {
        return new OperationResult(Collections.emptyList());
    }

    /**
     * 新增小B
     *
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> addSmallRentMechant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO) {
        return new OperationResult(RequestResultEnum.ROLE_NOT_EXEISTS, false);
    }

    /**
     * 修改小B
     *
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> modifySmallRentMechant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO) {
        return new OperationResult(RequestResultEnum.ROLE_NOT_EXEISTS, false);
    }

    /**
     * 删除小B
     *
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> removeSmallRentMechant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO) {
        return new OperationResult(RequestResultEnum.ROLE_NOT_EXEISTS, false);
    }
}
