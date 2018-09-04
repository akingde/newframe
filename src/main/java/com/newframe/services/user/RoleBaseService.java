package com.newframe.services.user;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.*;
import com.newframe.dto.user.response.ProductDTO;
import com.newframe.dto.user.response.ProductSupplierDTO;
import com.newframe.dto.user.response.UserRoleApplyDTO;
import com.newframe.dto.user.response.UserRoleDTO;
import com.newframe.entity.user.UserRoleApply;

import java.io.IOException;
import java.util.List;

/**
 * @author WangBin
 */
public interface RoleBaseService {

    /**
     * 角色申请
     * @param uid
     * @param role
     * @param roleId
     * @return
     */
    OperationResult<Boolean> roleApply(Long uid, RoleApplyDTO role, Integer roleId) throws IOException;

    /**
     * 撤销申请
     * @param uid
     * @param roleApplyId
     * @return
     */
    OperationResult<Boolean> revokeRoleApply(Long uid, Long roleApplyId);

    /**
     * 获取角色申请详细信息
     * @param uid
     * @param roleId
     * @param roleApplyId
     * @return
     */
    OperationResult<UserRoleApplyDTO> getUserApplyInfo(Long uid, Integer roleId, Long roleApplyId);

    /**
     * 获取用户角色信息
     * @param uid
     * @param roleId
     * @return
     */
    OperationResult<UserRoleDTO> getUserRoleInfo(Long uid, Integer roleId);

    /**
     * 获取所有的供应商
     * @param roleId
     * @return
     */
    OperationResult<List<UserRoleDTO.Supplier>> getAllSupplier(Integer roleId);

    /**
     * 获取指定的供应商
     * @param uid
     * @return
     */
    OperationResult<List<UserRoleDTO.Supplier>> getAppointSupplier(Long uid);

    /**
     * 设置指定供应商开关
     * @param uid
     * @param roleId
     * @param appoint
     * @return
     */
    OperationResult<Boolean> setAppoint(Long uid, Integer roleId, boolean appoint);

    /**
     * 修改指定供应商
     * @param uid
     * @param roleId
     * @param supplierUid
     * @param revokeSupplierUid
     * @return
     */
    OperationResult<Boolean> modifyAppointSupplier(Long uid, Integer roleId, List<Long> supplierUid, List<Long> revokeSupplierUid);

    /**
     * 获取小B列表
     * @param uid
     * @param roleId
     * @return
     */
    OperationResult<List<UserRoleDTO.SmallRentMechant>> getSmallRentMechantList(Long uid, Integer roleId);

    /**
     * 新增小B
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    OperationResult<Boolean> addSmallRentMechant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO) throws IOException;

    /**
     * 修改小B
     * @param uid
     * @param rentMerchantModifyDTO
     * @return
     */
    OperationResult<Boolean> modifySmallRentMechant(Long uid, RentMerchantModifyDTO rentMerchantModifyDTO);

    /**
     * 删除小B
     * @param uid
     * @param removeUid
     * @return
     */
    OperationResult<Boolean> removeSmallRentMechant(Long uid, Integer roleId, Long removeUid);

    /**
     * 后台审核角色通过
     * @return
     */
    OperationResult<Boolean> passRoleApply(UserRoleApply userRoleApply);

    /**
     * 获取商品列表
     * @param uid
     * @param condition
     * @return
     */
    OperationResult<ProductDTO> getProductList(Long uid, Integer roleId, PageSearchDTO condition);

    /**
     * 添加商品
     * @param condition
     * @return
     */
    OperationResult<Boolean> addProduct(Long uid, Integer roleId, ProductModifyDTO condition);

    /**
     * 修改商品
     * @param condition
     * @return
     */
    OperationResult<Boolean> modifyProduct(Long uid, Integer roleId, ProductModifyDTO condition);

    /**
     * 下架商品
     * @param uid
     * @param productId
     * @return
     */
    OperationResult<Boolean> removeProduct(Long uid, Integer roleId, Long productId);
}
