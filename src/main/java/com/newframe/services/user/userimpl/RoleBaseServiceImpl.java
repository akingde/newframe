package com.newframe.services.user.userimpl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.newframe.dto.OperationResult;
import com.newframe.dto.after.response.UserDTO;
import com.newframe.dto.user.request.*;
import com.newframe.dto.user.response.*;
import com.newframe.entity.user.*;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.user.PatternEnum;
import com.newframe.enums.user.RelationshipEnum;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.enums.user.RoleStatusEnum;
import com.newframe.services.user.RoleService;
import com.newframe.services.user.RoleBaseService;
import com.newframe.services.user.UserService;
import com.newframe.services.userbase.*;
import com.newframe.utils.BigDecimalUtils;
import com.newframe.utils.FileUtils;
import com.newframe.utils.IdNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author WangBin
 */
@Service
public class RoleBaseServiceImpl implements RoleBaseService {

    @Autowired
    private com.newframe.services.userbase.UserRoleService userRoleService;
    @Autowired
    private UserRoleApplyService userRoleApplyService;
    @Autowired
    private UserRentMerchantService userRentMerchantService;
    @Autowired
    private UserService userService;
    @Autowired
    private MerchantAppointService merchantAppointService;

    private Map<Integer, RoleService> roleServiceMap = new HashMap<>();

    public RoleBaseServiceImpl(List<RoleService> roleServices) {
        for (RoleService roleService : roleServices) {
            roleServiceMap.put(roleService.getRoleId(), roleService);
        }
    }

    /**
     * 角色申请校验
     * @param role
     * @return
     */
    private OperationResult<Boolean> roleApplyCheck(RoleApplyDTO role){
        if(StringUtils.isAnyEmpty(role.getName(), role.getLegalEntity(), role.getBusinessListenNumber(), role.getTopContacts(), role.getJob())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if(!PatternEnum.checkPattern(role.getTopContactsPhoneNumber(), PatternEnum.mobile)){
            return new OperationResult(RequestResultEnum.MOBILE_INVALID, false);
        }
        if(!PatternEnum.checkPattern(role.getContactsPhoneNumber(), PatternEnum.mobile)){
            return new OperationResult(RequestResultEnum.MOBILE_INVALID, false);
        }
        if (RelationshipEnum.isEmpty(role.getRelationship())){
            return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
        }
        if(!IdNumberUtils.isValidatedAllIdcard(role.getLegalEntityIdNumber())){
            return new OperationResult(RequestResultEnum.ID_NUMBER_ERROR, false);
        }
        if(!FileUtils.checkImg(role.getLegalEntityIdCardFront())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if(!FileUtils.checkImg(role.getLegalEntityIdCardBack())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if (FileUtils.checkImageAndEmpty(2, role.getBusinessListen())) {
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        return new OperationResult(true);
    }

    /**
     * 修改手机号
     *
     * @param uid
     * @param mobile
     * @return
     */
    @Override
    public OperationResult<Boolean> modifyMobile(Long uid, String mobile) {
        List<UserRole> roles = userRoleService.findAll(uid);
        if (CollectionUtils.isEmpty(roles)){
            return new OperationResult(true);
        }
        for (UserRole role : roles) {
            roleServiceMap.get(role.getRoleId()).modifyMobile(uid, mobile);
        }
        return new OperationResult(true);
    }

    /**
     * 角色申请
     *
     * @param uid
     * @param role
     * @param roleId
     * @return
     */
    @Override
    public OperationResult<Boolean> roleApply(Long uid, RoleApplyDTO role, Integer roleId) throws IOException {
        List<UserRoleApply> applyList = userRoleApplyService.findApplyList(uid);
        if(applyList != null && applyList.size() > 0){
            return new OperationResult(RequestResultEnum.ROLE_APPLY_TOO_MUCH, false);
        }
        List<UserRole> roleList = userRoleService.findAll(uid);
        if(CollectionUtils.isNotEmpty(roleList)){
            return new OperationResult(RequestResultEnum.ROLE_EXISTS, false);
        }
        OperationResult<Boolean> result = roleApplyCheck(role);
        if (!result.getEntity()){
            return result;
        }
        return roleServiceMap.get(roleId).roleApply(uid, role);
    }

    /**
     * 撤销申请
     *
     * @param uid
     * @param roleApplyId
     * @return
     */
    @Override
    public OperationResult<Boolean> revokeRoleApply(Long uid, Long roleApplyId) {
        UserRoleApply roleApply = userRoleApplyService.findOne(roleApplyId, uid);
        if(roleApply == null){
            return new OperationResult(RequestResultEnum.INVALID_ACCESS, false);
        }
        if (!roleApply.getApplyStatus().equals(RoleStatusEnum.UNDER_REVIEW.getRoleStatue())){
            return new OperationResult(RequestResultEnum.ROLE_REVOKE_RPPLY_ERROR, false);
        }
        UserRoleApply userRoleApply = new UserRoleApply();
        userRoleApply.setUid(uid);
        userRoleApply.setId(roleApplyId);
        userRoleApply.setApplyStatus(RoleStatusEnum.REVOKE.getRoleStatue());
        userRoleApplyService.updateByRoleApplyId(userRoleApply);
        return new OperationResult(true);
    }

    /**
     * 获取角色申请信息
     *
     * @param uid
     * @param roleId
     * @param roleApplyId
     * @return
     */
    @Override
    public OperationResult<UserRoleApplyDTO> getUserApplyInfo(Long uid, Integer roleId, Long roleApplyId) {
        return roleServiceMap.get(roleId).getUserRoleApplyInfo(uid, roleApplyId);
    }

    /**
     * 获取用户角色信息
     *
     * @param uid
     * @param roleId
     * @return
     */
    @Override
    public OperationResult<UserRoleDTO> getUserRoleInfo(Long uid, Integer roleId) {
        return roleServiceMap.get(roleId).getUserRoleInfo(uid);
    }

    /**
     * 获取所有的供应商
     *
     * @param roleId
     * @return
     */
    @Override
    public OperationResult<List<UserRoleDTO.Supplier>> getAllSupplier(Integer roleId) {
        return roleServiceMap.get(roleId).getAllSupplier();
    }

    /**
     * 获取指定的供应商
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<AppointSupplierDTO> getAppointSupplier(Long uid) {

        UserRentMerchant rentMerchant = userRentMerchantService.findOne(uid, RoleEnum.FIRST_RENT_MERCHANT.getRoleId());
        if(rentMerchant == null){
            return new OperationResult(RequestResultEnum.ROLE_NOT_EXEISTS);
        }
        if(!rentMerchant.getAppoint()){
            return new OperationResult(new AppointSupplierDTO(rentMerchant.getAppoint()));
        }
        OperationResult<List<UserRoleDTO.Supplier>> result =
                roleServiceMap.get(RoleEnum.SUPPLIER.getRoleId())
                                .getAppointSupplier(
                                        roleServiceMap.get(RoleEnum.FIRST_RENT_MERCHANT.getRoleId())
                                                        .getAppointSupplierUid(uid)
                                );
        return new OperationResult(new AppointSupplierDTO(rentMerchant.getAppoint(), result.getEntity()));
    }

    /**
     * 设置指定供应商开关
     *
     * @param uid
     * @param roleId
     * @param appoint
     * @return
     */
    @Override
    public OperationResult<Boolean> setAppoint(Long uid, Integer roleId, boolean appoint) {
        return roleServiceMap.get(roleId).setAppoint(uid, appoint);
    }

    /**
     * 修改指定供应商
     *
     * @param uid
     * @param roleId
     * @param supplierUid
     * @param revokeSupplierUid
     * @return
     */
    @Override
    public OperationResult<Boolean> modifyAppointSupplier(Long uid, Integer roleId, List<Long> supplierUid, List<Long> revokeSupplierUid) {
        List<Long> inLists = Lists.newArrayList();
        List<Long> reLists = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(supplierUid)){
            inLists.addAll(supplierUid.stream().distinct().collect(toList()));
        }
        if(CollectionUtils.isNotEmpty(revokeSupplierUid)){
            reLists.addAll(revokeSupplierUid.stream().distinct().collect(toList()));
        }
        long count = inLists.stream().filter(item -> reLists.contains(item)).count();
        if(count > 0){
            return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
        }
        UserRole userRole = userRoleService.findOne(uid, roleId, RoleStatusEnum.NORMAL.getRoleStatue());
        if (userRole == null){
            return new OperationResult(RequestResultEnum.ROLE_NOT_EXEISTS, false);
        }
        List<MerchantAppoint> insertList = roleServiceMap.get(roleId).getAppointSupplier(uid, supplierUid);
        List<MerchantAppoint> removeList = roleServiceMap.get(roleId).getAppointSupplier(uid, revokeSupplierUid);
        if (insertList.size() != 0 || reLists.size() != removeList.size()){
            return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
        }
        if (CollectionUtils.isNotEmpty(inLists)) {
            roleServiceMap.get(roleId).batchInsert(uid, inLists);
        }
        if (CollectionUtils.isNotEmpty(reLists)){
            roleServiceMap.get(roleId).removeAppointSupplier(removeList);
        }
        return new OperationResult(true);
    }

    /**
     * 修改指定供应商
     *
     * @param uid
     * @param roleId
     * @param supplierUid
     * @return
     */
    @Override
    public OperationResult<Boolean> modifyAppointSupplier(Long uid, Integer roleId, List<Long> supplierUid) {
        UserRole userRole = userRoleService.findOne(uid, roleId, RoleStatusEnum.NORMAL.getRoleStatue());
        if (userRole == null){
            return new OperationResult(RequestResultEnum.ROLE_NOT_EXEISTS, false);
        }
        List<MerchantAppoint> merchantAppoints = merchantAppointService.findAll(uid);
        if(CollectionUtils.isEmpty(supplierUid)){
            roleServiceMap.get(roleId).removeAppointSupplier(merchantAppoints);
            return new OperationResult(true);
        }
        List<Long> appoints = merchantAppoints.stream().map(MerchantAppoint::getSupplierUid).collect(toList());
        List<Long> inLists = supplierUid.stream().filter(item -> !appoints.contains(item)).distinct().collect(toList());
        List<Long> reLists = appoints.stream().filter(item -> !supplierUid.contains(item)).distinct().collect(toList());
        if (CollectionUtils.isNotEmpty(inLists)) {
            roleServiceMap.get(roleId).batchInsert(uid, inLists);
        }
        if (CollectionUtils.isNotEmpty(reLists)){
            List<MerchantAppoint> removeList = roleServiceMap.get(roleId).getAppointSupplier(uid, reLists);
            roleServiceMap.get(roleId).removeAppointSupplier(removeList);
        }
        return new OperationResult(true);
    }

    /**
     * 获取小B列表
     *
     * @param uid
     * @param  roleId
     * @return
     */
    @Override
    public OperationResult<SecondRentMerchantDTO> getSmallRentMechantList(Long uid, Integer roleId, PageSearchDTO pageSearchDTO) {
        return roleServiceMap.get(roleId).getSmallRentMechantList(uid, pageSearchDTO);
    }

    /**
     * 新增小B
     *
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationResult<Boolean> addSmallRentMechant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO) throws IOException{
        List<Area> areaList = userService.checkAddress(rentMerchantApplyDTO.getProvinceId(),
                rentMerchantApplyDTO.getCityId(),
                rentMerchantApplyDTO.getCountyId());
        if (areaList == null || StringUtils.isEmpty(rentMerchantApplyDTO.getConsigneeAddress())){
            return new OperationResult(RequestResultEnum.ADDRESS_ERROR, false);
        }
        OperationResult<Boolean> result = roleServiceMap.get(RoleEnum.FIRST_RENT_MERCHANT.getRoleId())
                .addSmallRentMechant(uid, rentMerchantApplyDTO, areaList);
        if (!result.getEntity()){
            return result;
        }
        return roleServiceMap.get(RoleEnum.SECOND_RENT_MERCHANT.getRoleId())
                .addSmallRentMechant(uid, rentMerchantApplyDTO, areaList);
    }

    /**
     * 修改小B
     *
     * @param uid
     * @param rentMerchantModifyDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> modifySmallRentMechant(Long uid, RentMerchantModifyDTO rentMerchantModifyDTO) {
        List<Area> areaList = userService.checkAddress(rentMerchantModifyDTO.getProvinceId(),
                rentMerchantModifyDTO.getCityId(),
                rentMerchantModifyDTO.getCountyId());
        if (areaList == null || StringUtils.isEmpty(rentMerchantModifyDTO.getConsigneeAddress())){
            return new OperationResult(RequestResultEnum.ADDRESS_ERROR, false);
        }
        UserRentMerchant merchant = userRentMerchantService.findOne(uid, RoleEnum.FIRST_RENT_MERCHANT.getRoleId());
        if(merchant == null){
            return new OperationResult(RequestResultEnum.ROLE_NOT_EXEISTS, false);
        }
        OperationResult<Boolean> modifyResult = roleServiceMap.get(RoleEnum.FIRST_RENT_MERCHANT.getRoleId())
                .modifySmallRentMechant(uid, rentMerchantModifyDTO, areaList);
        if (!modifyResult.getEntity()){
            return modifyResult;
        }
        Long modifyUid = rentMerchantModifyDTO.getModifyUid();
        String merchantPhone = rentMerchantModifyDTO.getMerchantPhone();

        OperationResult<Boolean> result = userService.modifyPhoneNumber(modifyUid, merchantPhone);
        if (!result.getEntity()){
            return result;
        }
        return roleServiceMap.get(RoleEnum.SECOND_RENT_MERCHANT.getRoleId())
                .modifySmallRentMechant(uid, rentMerchantModifyDTO, areaList);
    }

    /**
     * 删除小B
     *
     * @param uid
     * @param roleId
     * @return
     */
    @Override
    public OperationResult<Boolean> removeSmallRentMechant(Long uid, Integer roleId, Long removeUid) {
        return roleServiceMap.get(roleId).removeSmallRentMechant(uid, removeUid);
    }

    /**
     * 后台审核角色通过
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationResult<Boolean> passRoleApply(UserRoleApply userRoleApply) {
        userRoleApply.setApplyStatus(RoleStatusEnum.NORMAL.getRoleStatue());
        userRoleApply.setCheckUid(new UserDTO().getUid());
        userRoleApply.setCheckPerson(new UserDTO().getUserName());
        userRoleApplyService.updateByRoleApplyId(userRoleApply);
        Integer[] roleIds = RoleEnum.getRoleIdsByRoleId(userRoleApply.getRoleId());
        for (Integer roleId : roleIds) {
            roleServiceMap.get(roleId).passCheck(userRoleApply);
        }
        roleServiceMap.get(userRoleApply.getRoleId()).roleInBlock(userRoleApply);
        return new OperationResult(true);
    }

    /**
     * 添加账户信息
     *
     * @param uid
     * @param roleId
     * @return
     */
    @Override
    public OperationResult<Boolean> addAccount(Long uid, Integer roleId, UserRoleApply userRoleApply) {
        return roleServiceMap.get(roleId).addAccount(uid, userRoleApply);
    }

    /**
     * 获取商品列表
     *
     * @param uid
     * @param roleId
     * @param condition
     * @return
     */
    @Override
    public OperationResult<ProductDTO> getProductList(Long uid, Integer roleId, PageSearchDTO condition) {
        if (roleServiceMap.get(roleId) == null){
            return new OperationResult(RequestResultEnum.ROLE_ERROR);
        }
        return roleServiceMap.get(roleId).getProductList(uid, condition);
    }

    /**
     * 添加商品
     *
     * @param uid
     * @param roleId
     * @param condition
     * @return
     */
    @Override
    public OperationResult<Boolean> addProduct(Long uid, Integer roleId, ProductModifyDTO condition) {
        if (roleServiceMap.get(roleId) == null) {
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if(StringUtils.isEmpty(condition.getBrand())){
            return new OperationResult<>(RequestResultEnum.PARAMETER_ERROR, false);
        }
        if(StringUtils.isEmpty(condition.getModel())){
            return new OperationResult<>(RequestResultEnum.PARAMETER_ERROR, false);
        }
        if(StringUtils.isEmpty(condition.getColor())){
            return new OperationResult<>(RequestResultEnum.PARAMETER_ERROR, false);
        }
        if(condition.getRam() == null || condition.getRam() <= 0){
            return new OperationResult<>(RequestResultEnum.PARAMETER_ERROR, false);
        }
        if(condition.getRom() == null || condition.getRom() <= 0){
            return new OperationResult<>(RequestResultEnum.PARAMETER_ERROR, false);
        }
        if(!BigDecimalUtils.compareTo(condition.getGuidePrice())){
            return new OperationResult<>(RequestResultEnum.PARAMETER_ERROR, false);
        }
        if(!BigDecimalUtils.compareTo(condition.getSupplyPrice())){
            return new OperationResult<>(RequestResultEnum.PARAMETER_ERROR, false);
        }
        if(condition.getSurplusStock() == null && condition.getSurplusStock() <= 0){
            return new OperationResult<>(RequestResultEnum.PARAMETER_ERROR, false);
        }
        return roleServiceMap.get(roleId).addProduct(uid, condition);
    }

    /**
     * 修改商品
     *
     * @param uid
     * @param roleId
     * @param condition
     * @return
     */
    @Override
    public OperationResult<Boolean> modifyProduct(Long uid, Integer roleId, ProductModifyDTO condition) {
        if (roleServiceMap.get(roleId) == null) {
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        return roleServiceMap.get(roleId).modifyProduct(uid, condition);
    }

    /**
     * 下架商品
     *
     * @param uid
     * @param roleId
     * @param productId
     * @return
     */
    @Override
    public OperationResult<Boolean> removeProduct(Long uid, Integer roleId, Long productId) {
        if (roleServiceMap.get(roleId) == null) {
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        return roleServiceMap.get(roleId).removeProduct(uid, productId);
    }
}
