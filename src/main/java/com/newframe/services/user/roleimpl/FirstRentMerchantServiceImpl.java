package com.newframe.services.user.roleimpl;

import com.google.common.collect.Lists;
import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.*;
import com.newframe.dto.user.response.ProductDTO;
import com.newframe.dto.user.response.ProductSupplierDTO;
import com.newframe.dto.user.response.UserRoleApplyDTO;
import com.newframe.dto.user.response.UserRoleDTO;
import com.newframe.entity.user.*;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.user.PatternEnum;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.enums.user.RoleStatusEnum;
import com.newframe.services.common.AliossService;
import com.newframe.services.user.RoleService;
import com.newframe.services.userbase.*;
import com.newframe.utils.FileUtils;
import com.newframe.utils.IdNumberUtils;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangBin
 */
@Service
public class FirstRentMerchantServiceImpl implements RoleService {

    @Autowired
    private UserRoleApplyService userRoleApplyService;
    @Autowired
    private UserRentMerchantService userRentMerchantService;
    @Autowired
    private IdGlobalGenerator idGlobalGenerator;
    @Autowired
    private MerchantAppointService merchantAppointService;
    @Autowired
    private AliossService aliossService;
    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private UserHirerService userHirerService;
    @Autowired
    private UserSupplierService userSupplierService;

    private static final String bucket = "fzmsupplychain";

    @Override
    public Integer getRoleId() {
        return RoleEnum.FIRST_RENT_MERCHANT.getRoleId();
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
        RentMerchantApplyDTO roleApply = (RentMerchantApplyDTO)roleApplyDTO;
        if(!FileUtils.checkImageAndEmpty(2, roleApply.getHighestDegreeDiploma())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if(!FileUtils.checkImageAndEmpty(2, roleApply.getDrivingLicense())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if(!FileUtils.checkImageAndEmpty(2, roleApply.getHouseProprietaryCertificate())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        List<String> businessUrls =
                aliossService.uploadFilesToBasetool(roleApply.getBusinessListen(), bucket);
        List<String> highestUrls =
                aliossService.uploadFilesToBasetool(roleApply.getHighestDegreeDiploma(), bucket);
        List<String> drivindUrls =
                aliossService.uploadFilesToBasetool(roleApply.getDrivingLicense(), bucket);
        List<String> houseUrls =
                aliossService.uploadFilesToBasetool(roleApply.getHouseProprietaryCertificate(), bucket);

        UserRoleApply userRoleApply = new UserRoleApply();
        userRoleApply.setUid(uid);
        userRoleApply.setRoleId(getRoleId());
        userRoleApply.setPhoneNumber(userBaseInfoService.findOne(uid).getPhoneNumber());
        userRoleApply.setMerchantName(roleApply.getName());
        userRoleApply.setLegalEntity(roleApply.getLegalEntity());
        userRoleApply.setLegalEntityIdNumber(roleApply.getLegalEntityIdNumber());
        userRoleApply.setBusinessLicenseNumber(roleApply.getBusinessListenNumber());
        userRoleApply.setBusinessLicenseFile(String.join(",", businessUrls));
        userRoleApply.setHighestDegreeDiplomaFile(String.join(",", highestUrls));
        userRoleApply.setDrivingLicenseFile(String.join(",", drivindUrls));
        userRoleApply.setHouseProprietaryCertificateFile(String.join(",", houseUrls));
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
        return new OperationResult(roleApply == null ? new UserRoleDTO() : new UserRoleApplyDTO.RentMechant(roleApply));
    }

    /**
     * 通过角色审核
     *
     * @param userRoleApply
     * @return
     */
    @Override
    public OperationResult<Boolean> passCheck(UserRoleApply userRoleApply) {

        userRentMerchantService.insert(new UserRentMerchant(userRoleApply));
        userSupplierService.insert(new UserSupplier(userRoleApply));
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
        UserRentMerchant rentMerchant = userRentMerchantService.findOne(uid);
        return new OperationResult(rentMerchant == null ? new UserRoleDTO() : new UserRoleDTO.RentMechant(rentMerchant));
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
        UserRentMerchant rentMerchant = userRentMerchantService.findOne(uid, getRoleId());
        if (rentMerchant == null){
            return new OperationResult(RequestResultEnum.ROLE_NOT_EXEISTS, false);
        }
        rentMerchant.setAppoint(appoint);
        userRentMerchantService.update(rentMerchant);
        return new OperationResult(true);
    }

    /**
     * 获取指定的供应商uid
     *
     * @param uid
     * @return
     */
    @Override
    public List<Long> getAppointSupplierUid(Long uid) {
        List<MerchantAppoint> appointList = merchantAppointService.findAll(uid);
        if (appointList == null){
            return Lists.newArrayList();
        }
        return appointList.stream()
                .map(MerchantAppoint::getSupplierUid)
                .collect(Collectors.toList());
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
        List<MerchantAppoint> merchantAppoints = Lists.newArrayList();
        for (Long supplier : supplierUid) {
            MerchantAppoint merchantAppoint = new MerchantAppoint();
            merchantAppoint.setId(idGlobalGenerator.getSeqId(MerchantAppoint.class));
            merchantAppoint.setRentMerchantUid(uid);
            merchantAppoint.setSupplierUid(supplier);
            merchantAppoints.add(merchantAppoint);
        }
        return new OperationResult(merchantAppointService.batchInsert(merchantAppoints));
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
        return merchantAppointService.findAll(uid, supplierUid);
    }

    /**
     * 删除操作
     *
     * @param merchantAppoints
     */
    @Override
    public void removeAppointSupplier(List<MerchantAppoint> merchantAppoints) {
        merchantAppointService.remove(merchantAppoints);
    }

    /**
     * 根据uid获取小B列表
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<List<UserRoleDTO.SmallRentMechant>> getSmallRentMechantList(Long uid) {
        List<UserRentMerchant> merchants = userRentMerchantService.findAll(uid);
        if (merchants == null){
            return new OperationResult(Lists.newArrayList());
        }
        List<UserRoleDTO.SmallRentMechant> result = Lists.newArrayList();
        for (UserRentMerchant merchant : merchants) {
            result.add(new UserRoleDTO.SmallRentMechant(merchant));
        }
        return new OperationResult(result);
    }

    /**
     * 新增小B
     *
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> addSmallRentMechant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO,
                                                        List<Area> areaList) throws  IOException{
        if (FileUtils.checkImageAndEmpty(2, rentMerchantApplyDTO.getBusinessListen())) {
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if(!FileUtils.checkImage(2, rentMerchantApplyDTO.getHighestDegreeDiploma())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if(!FileUtils.checkImage(2, rentMerchantApplyDTO.getDrivingLicense())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if(!FileUtils.checkImage(2, rentMerchantApplyDTO.getHouseProprietaryCertificate())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if (StringUtils.isEmpty(rentMerchantApplyDTO.getConsigneeAddress())){
            return new OperationResult(RequestResultEnum.ADDRESS_ERROR, false);
        }
        return new OperationResult(true);
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
        if (rentMerchantModifyDTO.getModifyUid() == null){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        UserRentMerchant small = userRentMerchantService.findOne(rentMerchantModifyDTO.getModifyUid(),
                RoleEnum.SECOND_RENT_MERCHANT.getRoleId());
        if (small == null || !small.getParentId().equals(uid)){
            return new OperationResult(RequestResultEnum.INVALID_ACCESS, false);
        }
        if(!PatternEnum.checkPattern(rentMerchantModifyDTO.getMerchantPhone(), PatternEnum.mobile)){
            return new OperationResult(RequestResultEnum.MOBILE_INVALID, false);
        }
        if (StringUtils.isEmpty(rentMerchantModifyDTO.getName())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if (StringUtils.isEmpty(rentMerchantModifyDTO.getLegalEntity())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if(IdNumberUtils.isValidatedAllIdcard(rentMerchantModifyDTO.getLegalEntityIdNumber())){
            return new OperationResult(RequestResultEnum.ID_NUMBER_ERROR, false);
        }
        if(!FileUtils.isAllCorrect(2, rentMerchantModifyDTO.getBusinessListen())) {
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if (FileUtils.isAllCorrect(2, rentMerchantModifyDTO.getHighestDegreeDiploma())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if (FileUtils.isAllCorrect(2, rentMerchantModifyDTO.getDrivingLicense())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if(FileUtils.isAllCorrect(9, rentMerchantModifyDTO.getHouseProprietaryCertificate())){
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if (StringUtils.isEmpty(rentMerchantModifyDTO.getConsigneeAddress())){
            return new OperationResult(RequestResultEnum.ADDRESS_ERROR, false);
        }
        return new OperationResult(true);
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
        return null;
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