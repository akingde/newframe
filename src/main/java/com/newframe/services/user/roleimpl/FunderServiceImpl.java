package com.newframe.services.user.roleimpl;

import com.google.common.collect.Lists;
import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.*;
import com.newframe.dto.user.response.ProductDTO;
import com.newframe.dto.user.response.UserRoleApplyDTO;
import com.newframe.dto.user.response.UserRoleDTO;
import com.newframe.entity.user.*;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.user.PatternEnum;
import com.newframe.enums.user.RelationshipEnum;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.enums.user.RoleStatusEnum;
import com.newframe.services.common.AliossService;
import com.newframe.services.user.RoleService;
import com.newframe.services.userbase.*;
import com.newframe.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    @Autowired
    private AliossService aliossService;
    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private UserHirerService userHirerService;
    @Autowired
    private UserRoleService userRoleService;

    private static final String bucket = "fzmsupplychain";

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
    public OperationResult<Boolean> roleApply(Long uid, RoleApplyDTO roleApplyDTO) throws IOException {
        FunderApplyDTO roleApply = (FunderApplyDTO)roleApplyDTO;
        if(StringUtils.isAnyEmpty(roleApply.getTopContacts()) || RelationshipEnum.isEmpty(roleApply.getRelationship())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if (!PatternEnum.checkPattern(roleApply.getTopContactsPhoneNumber(), PatternEnum.mobile)){
            return new OperationResult(RequestResultEnum.MOBILE_INVALID, false);
        }
        if(FileUtils.checkImageAndEmpty(2, roleApply.getBusinessQualification())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if(FileUtils.checkImageAndEmpty(2, roleApply.getLetterOfAttorney())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        List<String> businessUrls =
                aliossService.uploadFilesToBasetool(roleApply.getBusinessListen(), bucket);
        List<String> qualificationUrls =
                aliossService.uploadFilesToBasetool(roleApply.getBusinessQualification(), bucket);
        List<String> letterUrls =
                aliossService.uploadFilesToBasetool(roleApply.getLetterOfAttorney(), bucket);
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
        userRoleApply.setBusinessQualificationFile(String.join(",", qualificationUrls));
        userRoleApply.setLetterOfAttorneyFile(String.join(",", letterUrls));
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
        return roleApply == null ? new OperationResult() : new OperationResult(new UserRoleApplyDTO.Funder(roleApply));
    }

    /**
     * 通过角色审核
     *
     * @param userRoleApply
     * @return
     */
    @Override
    public OperationResult<Boolean> passCheck(UserRoleApply userRoleApply) {
        Integer[] roleIds = new Integer[]{RoleEnum.FUNDER.getRoleId(), RoleEnum.HIRER.getRoleId()};
        userRoleService.batchInsert(userRoleApply.getUid(), roleIds);
        userFunderService.insert(new UserFunder(userRoleApply));
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
        UserFunder funder = userFunderService.findOne(uid);
        return funder == null ? new OperationResult() : new OperationResult(new UserRoleDTO.Funder(funder));
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
    public OperationResult<List<MerchantAppoint>> batchInsert(Long uid, List<Long> supplierUid) {
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
    public List<MerchantAppoint> getAppointSupplier(Long uid, List<Long> supplierUid) {
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
        return new OperationResult(RequestResultEnum.ROLE_ERROR);
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
        return new OperationResult(RequestResultEnum.ROLE_ERROR, false);
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
        return new OperationResult(RequestResultEnum.ROLE_ERROR, false);
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
        return new OperationResult(RequestResultEnum.ROLE_ERROR, false);
    }
}
