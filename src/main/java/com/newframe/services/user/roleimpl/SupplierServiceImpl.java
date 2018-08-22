package com.newframe.services.user.roleimpl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.RoleApplyDTO;
import com.newframe.dto.user.response.UserRoleApplyDTO;
import com.newframe.dto.user.response.UserRoleDTO;
import com.newframe.entity.user.UserRoleApply;
import com.newframe.entity.user.UserSupplier;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.services.user.RoleService;
import com.newframe.services.userbase.UserRoleApplyService;
import com.newframe.services.userbase.UserSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author WangBin
 */
@Service
public class SupplierServiceImpl implements RoleService {

    @Autowired
    private UserRoleApplyService userRoleApplyService;
    @Autowired
    private UserSupplierService userSupplierService;


    @Override
    public Integer getRoleId() {
        return RoleEnum.SUPPLIER.getRoleId();
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
        return new OperationResult(roleApply == null ? new UserRoleDTO() : new UserRoleApplyDTO.Supplier(roleApply));
    }

    /**
     * 根据uid获取角色信息
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<UserRoleDTO> getUserRoleInfo(Long uid) {
        UserSupplier supplier = userSupplierService.findOne(uid);
        return new OperationResult(supplier == null ? new UserRoleDTO() : new UserRoleDTO.Supplier(supplier));
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
        List<UserSupplier> supplierList = userSupplierService.findAll();
        List<UserRoleDTO.Supplier> suppliers = Collections.emptyList();
        for (UserSupplier supplier : supplierList) {
            suppliers.add(new UserRoleDTO.Supplier(supplier));
        }
        return new OperationResult(suppliers);
    }
}
