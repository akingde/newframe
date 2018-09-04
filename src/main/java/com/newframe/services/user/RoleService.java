package com.newframe.services.user;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.*;
import com.newframe.dto.user.response.ProductDTO;
import com.newframe.dto.user.response.ProductSupplierDTO;
import com.newframe.dto.user.response.UserRoleApplyDTO;
import com.newframe.dto.user.response.UserRoleDTO;
import com.newframe.entity.user.Area;
import com.newframe.entity.user.MerchantAppoint;
import com.newframe.entity.user.UserRoleApply;

import java.io.IOException;
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
    OperationResult<Boolean> roleApply(Long uid, RoleApplyDTO roleApplyDTO) throws IOException;

    /**
     * 获取角色申请详细信息
     * @param uid
     * @param roleApplyId
     * @return
     */
    OperationResult<UserRoleApplyDTO> getUserRoleApplyInfo(Long uid, Long roleApplyId);

    /**
     * 通过角色审核
     * @param userRoleApply
     * @return
     */
    OperationResult<Boolean> passCheck(UserRoleApply userRoleApply);

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
    OperationResult<List<MerchantAppoint>> batchInsert(Long uid, List<Long> supplierUid);

    /**
     * 根据供应商id找出供应商信息
     * @param uid
     * @param supplierUid
     * @return
     */
    List<MerchantAppoint> getAppointSupplier(Long uid, List<Long> supplierUid);

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
     * @param areaList
     * @return
     */
    OperationResult<Boolean> addSmallRentMechant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO, List<Area> areaList) throws IOException;

    /**
     * 修改小B
     * @param uid
     * @param rentMerchantModifyDTO
     * @param areaList
     * @return
     */
    OperationResult<Boolean> modifySmallRentMechant(Long uid, RentMerchantModifyDTO rentMerchantModifyDTO, List<Area> areaList);

    /**
     * 删除小B
     * @param uid
     * @param removeUid
     * @return
     */
    OperationResult<Boolean> removeSmallRentMechant(Long uid, Long removeUid);

    /**
     * 生成角色记录
     * @param roleId
     * @return
     */
    OperationResult<Boolean> insertRole(Integer roleId);

    /**
     * 获取商品列表
     * @param uid
     * @param condition
     * @return
     */
    OperationResult<ProductDTO> getProductList(Long uid, PageSearchDTO condition);

    /**
     * 添加商品
     * @param condition
     * @return
     */
    OperationResult<Boolean> addProduct(Long uid, ProductModifyDTO condition);

    /**
     * 修改商品
     * @param condition
     * @return
     */
    OperationResult<Boolean> modifyProduct(Long uid, ProductModifyDTO condition);

    /**
     * 下架商品
     * @param uid
     * @param productId
     * @return
     */
    OperationResult<Boolean> removeProduct(Long uid, Long productId);
}