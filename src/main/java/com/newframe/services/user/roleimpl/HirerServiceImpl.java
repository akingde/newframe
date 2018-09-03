package com.newframe.services.user.roleimpl;

import com.google.common.collect.Lists;
import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.*;
import com.newframe.dto.user.response.*;
import com.newframe.entity.user.*;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.user.PatternEnum;
import com.newframe.enums.user.RelationshipEnum;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.enums.user.RoleStatusEnum;
import com.newframe.services.common.AliossService;
import com.newframe.services.user.RoleService;
import com.newframe.services.userbase.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    @Autowired
    private AliossService aliossService;
    @Autowired
    private ProductLessorService productLessorService;
    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private UserRoleService userRoleService;

    private static final String bucket = "fzmsupplychain";

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
    public OperationResult<Boolean> roleApply(Long uid, RoleApplyDTO roleApplyDTO) throws IOException {
        HirerApplyDTO roleApply = (HirerApplyDTO)roleApplyDTO;
        if(StringUtils.isAnyEmpty(roleApply.getTopContacts()) || RelationshipEnum.isEmpty(roleApply.getRelationship())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if (!PatternEnum.checkPattern(roleApply.getTopContactsPhoneNumber(), PatternEnum.mobile)){
            return new OperationResult(RequestResultEnum.MOBILE_INVALID, false);
        }
        List<String> businessUrls = aliossService.uploadFilesToBasetool(roleApply.getBusinessListen(), bucket);
        UserRoleApply userRoleApply = new UserRoleApply();
        userRoleApply.setUid(uid);
        userRoleApply.setRoleId(getRoleId());
        userRoleApply.setPhoneNumber(userBaseInfoService.findOne(uid).getPhoneNumber());
        userRoleApply.setMerchantName(roleApply.getName());
        userRoleApply.setLegalEntity(roleApply.getLegalEntity());
        userRoleApply.setLegalEntityIdNumber(roleApply.getLegalEntityIdNumber());
        userRoleApply.setTopContacts(roleApply.getTopContacts());
        userRoleApply.setRelationship(roleApply.getRelationship());
        userRoleApply.setTopContactsPhoneNumber(roleApply.getTopContactsPhoneNumber());
        userRoleApply.setBusinessLicenseNumber(roleApply.getBusinessListenNumber());
        userRoleApply.setBusinessLicenseFile(String.join(",", businessUrls));
        userRoleApply.setApplyStatus(RoleStatusEnum.UNDER_REVIEW.getRoleStatue());
        userRoleApplyService.insert(userRoleApply);
        return new OperationResult(true);
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
        return roleApply == null ? new OperationResult() : new OperationResult(new UserRoleApplyDTO.Hirer(roleApply));
    }

    /**
     * 通过角色审核
     *
     * @param userRoleApply
     * @return
     */
    @Override
    public OperationResult<Boolean> passCheck(UserRoleApply userRoleApply) {
        UserRole userRole = new UserRole();
        userRole.setUid(userRoleApply.getUid());
        userRole.setRoleId(RoleEnum.HIRER.getRoleId());
        userRoleService.insert(userRole);
        userHirerService.insert(new UserHirer(userRoleApply));
        return new OperationResult(true);
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
        return hirer == null ? new OperationResult() : new OperationResult(new UserRoleDTO.Hirer(hirer));
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
        return Lists.newArrayList();
    }

    /**
     * 根据供应商id找出供应商信息
     *
     * @param supplierUid
     * @return
     */
    @Override
    public OperationResult<List<UserRoleDTO.Supplier>> getAppointSupplier(List<Long> supplierUid) {
        return new OperationResult(Lists.newArrayList());
    }

    /**
     * 获取所有的供应商
     *
     * @return
     */
    @Override
    public OperationResult<List<UserRoleDTO.Supplier>> getAllSupplier() {
        return new OperationResult(Lists.newArrayList());
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
        return Lists.newArrayList();
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
        return new OperationResult(Lists.newArrayList());
    }

    /**
     * 新增小B
     *
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    public OperationResult<Boolean> addSmallRentMechant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO,
                                                        List<Area> areaList) throws  IOException{
        return new OperationResult(RequestResultEnum.ROLE_ERROR, false);
    }

    /**
     * 修改小B
     *
     * @param uid
     * @param rentMerchantModifyDTO
     * @param areaList
     * @return
     */
    @Override
    public OperationResult<Boolean> modifySmallRentMechant(Long uid, RentMerchantModifyDTO rentMerchantModifyDTO, List<Area> areaList) {
        return new OperationResult(RequestResultEnum.ROLE_ERROR, false);
    }

    /**
     * 删除小B
     *
     * @param uid
     * @param removeUid
     * @return
     */
    @Override
    public OperationResult<Boolean> removeSmallRentMechant(Long uid, Long removeUid) {
        return new OperationResult(RequestResultEnum.ROLE_ERROR, false);
    }

    /**
     * 生成角色记录
     *
     * @param roleId
     * @return
     */
    @Override
    public OperationResult<Boolean> insertRole(Integer roleId) {
        return null;
    }

    /**
     * 获取商品列表
     *
     * @param uid
     * @param condition
     * @return
     */
    @Override
    public OperationResult<ProductDTO> getProductList(Long uid, PageSearchDTO condition) {
        Page<ProductLessor> page = productLessorService.findAll(uid, condition);
        if (page == null){
            return new OperationResult(new ProductDTO(0L));
        }
        List<ProductLessor> content = page.getContent();
        List<ProductHirerDTO> list = Lists.newArrayList();
        for (ProductLessor productLessor : content) {
            list.add(new ProductHirerDTO(productLessor));
        }
        return new OperationResult(new ProductDTO(page.getTotalElements(), list));
    }

    /**
     * 添加商品
     *
     * @param uid
     * @param condition
     * @return
     */
    @Override
    public OperationResult<Boolean> addProduct(Long uid, ProductModifyDTO condition) {
        ProductLessor productLessor = new ProductLessor(uid, condition);
        productLessorService.insert(productLessor);
        return new OperationResult(true);
    }

    /**
     * 修改商品
     *
     * @param uid
     * @param condition
     * @return
     */
    @Override
    public OperationResult<Boolean> modifyProduct(Long uid, ProductModifyDTO condition) {
        ProductLessor productLessor = new ProductLessor(uid, condition);
        productLessorService.modify(productLessor);
        return new OperationResult(true);
    }

    /**
     * 下架商品
     *
     * @param uid
     * @param productId
     * @return
     */
    @Override
    public OperationResult<Boolean> removeProduct(Long uid, Long productId) {
        productLessorService.revoke(uid, productId);
        return new OperationResult(true);
    }
}
