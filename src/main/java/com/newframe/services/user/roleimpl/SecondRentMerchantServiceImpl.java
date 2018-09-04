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
import com.newframe.enums.user.UserStatusEnum;
import com.newframe.services.common.AliossService;
import com.newframe.services.user.RoleService;
import com.newframe.services.user.SessionService;
import com.newframe.services.user.UserService;
import com.newframe.services.userbase.UserBaseInfoService;
import com.newframe.services.userbase.UserPwdService;
import com.newframe.services.userbase.UserRentMerchantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangBin
 */
@Service
public class SecondRentMerchantServiceImpl implements RoleService {

    @Autowired
    private UserRentMerchantService userRentMerchantService;
    @Autowired
    private AliossService aliossService;
    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserPwdService userPwdService;

    private static final String bucket = "fzmsupplychain";

    @Override
    public Integer getRoleId() {
        return RoleEnum.SECOND_RENT_MERCHANT.getRoleId();
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
        return new OperationResult(RequestResultEnum.INVALID_ACCESS, false);
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
        return new OperationResult();
    }

    /**
     * 通过角色审核
     *
     * @param userRoleApply
     * @return
     */
    @Override
    public OperationResult<Boolean> passCheck(UserRoleApply userRoleApply) {
        return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
    }

    /**
     * 根据uid获取角色信息
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<UserRoleDTO> getUserRoleInfo(Long uid) {
        UserRentMerchant rentMerchant = userRentMerchantService.findOne(uid, getRoleId());
        return rentMerchant == null ? new OperationResult() : new OperationResult(new UserRoleDTO.RentMechant(rentMerchant));
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
        return new OperationResult(RequestResultEnum.ROLE_ERROR, false);
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
        if(!PatternEnum.checkPattern(rentMerchantApplyDTO.getMerchantPhone(), PatternEnum.mobile)){
            return new OperationResult(RequestResultEnum.MOBILE_INVALID, false);
        }
        if (userBaseInfoService.checkMmobileExists(rentMerchantApplyDTO.getMerchantPhone())){
            return new OperationResult(RequestResultEnum.MOBILE_EXISTS, false);
        }
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setPhoneNumber(rentMerchantApplyDTO.getMerchantPhone());
        userBaseInfo.setUserStatus(RoleStatusEnum.NORMAL.getRoleStatue());
        UserBaseInfo baseInfo = userBaseInfoService.insert(userBaseInfo);
        UserPwd userPwd = new UserPwd();
        userPwd.setUid(baseInfo.getUid());
        userPwdService.insert(userPwd);
        sessionService.setAppUserToken(baseInfo.getUid());
        sessionService.setWebUserToken(baseInfo.getUid());
        List<String> businessUrls =
                aliossService.uploadFilesToBasetool(rentMerchantApplyDTO.getBusinessListen(), bucket);
        List<String> highestUrls =
                aliossService.uploadFilesToBasetool(rentMerchantApplyDTO.getHighestDegreeDiploma(), bucket);
        List<String> drivindUrls =
                aliossService.uploadFilesToBasetool(rentMerchantApplyDTO.getDrivingLicense(), bucket);
        List<String> houseUrls =
                aliossService.uploadFilesToBasetool(rentMerchantApplyDTO.getHouseProprietaryCertificate(), bucket);
        List<Area> areas = areaList.stream().sorted(Comparator.comparing(Area::getAreaLevel)).collect(Collectors.toList());
        String provinceName = areas.get(0).getAreaName();
        String cityName = areas.get(1).getAreaName();
        String countyName = areas.get(2).getAreaName();
        String address = provinceName + cityName + countyName + rentMerchantApplyDTO.getConsigneeAddress();
        UserRentMerchant rentMerchant = new UserRentMerchant();
        rentMerchant.setUid(baseInfo.getUid());
        rentMerchant.setRoleId(RoleEnum.SECOND_RENT_MERCHANT.getRoleId());
        rentMerchant.setMerchantPhoneNumber(rentMerchantApplyDTO.getMerchantPhone());
        rentMerchant.setMerchantName(rentMerchantApplyDTO.getName());
        rentMerchant.setLegalEntity(rentMerchantApplyDTO.getLegalEntity());
        rentMerchant.setLegalEntityIdNumber(rentMerchantApplyDTO.getLegalEntityIdNumber());
        rentMerchant.setRentMerchantAddress(address);
        rentMerchant.setBusinessLicenseNumber(rentMerchantApplyDTO.getBusinessListenNumber());
        rentMerchant.setBusinessLicenseFile(StringUtils.join(",", businessUrls));
        rentMerchant.setHighestDegreeDiplomaFile(StringUtils.join(",", highestUrls));
        rentMerchant.setDrivingLicenseFile(StringUtils.join(",", drivindUrls));
        rentMerchant.setHouseProprietaryCertificateFile(StringUtils.join(",", houseUrls));
        rentMerchant.setRoleStatus(RoleStatusEnum.NORMAL.getRoleStatue());
        rentMerchant.setParentId(uid);
        rentMerchant.setProvinceId(rentMerchantApplyDTO.getProvinceId());
        rentMerchant.setProvinceName(provinceName);
        rentMerchant.setCityId(rentMerchantApplyDTO.getCityId());
        rentMerchant.setCityName(cityName);
        rentMerchant.setCountyId(rentMerchantApplyDTO.getCountyId());
        rentMerchant.setCountyName(countyName);
        rentMerchant.setConsigneeAddress(rentMerchantApplyDTO.getConsigneeAddress());
        userRentMerchantService.insert(rentMerchant);
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
        if(StringUtils.isNotEmpty(rentMerchantModifyDTO.getMerchantPhone())){
            String phoneNumber = userBaseInfoService.findOne(rentMerchantModifyDTO.getModifyUid()).getPhoneNumber();
            if (!phoneNumber.equals(rentMerchantModifyDTO.getMerchantPhone())) {
                if(userBaseInfoService.checkMmobileExists(rentMerchantModifyDTO.getMerchantPhone())){
                    return new OperationResult(RequestResultEnum.MOBILE_EXISTS, true);
                }
            }
        }
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUid(rentMerchantModifyDTO.getModifyUid());
        userBaseInfo.setPhoneNumber(rentMerchantModifyDTO.getMerchantPhone());
        userBaseInfoService.updateByUid(userBaseInfo);
        List<Area> areas = areaList.stream().sorted(Comparator.comparing(Area::getAreaLevel)).collect(Collectors.toList());
        String provinceName = areas.get(0).getAreaName();
        String cityName = areas.get(1).getAreaName();
        String countyName = areas.get(2).getAreaName();
        String address = provinceName + cityName + countyName + rentMerchantModifyDTO.getConsigneeAddress();
        UserRentMerchant small = new UserRentMerchant();
        small.setUid(rentMerchantModifyDTO.getModifyUid());
        small.setMerchantPhoneNumber(rentMerchantModifyDTO.getMerchantPhone());
        small.setMerchantName(rentMerchantModifyDTO.getName());
        small.setLegalEntity(rentMerchantModifyDTO.getLegalEntity());
        small.setLegalEntityIdNumber(rentMerchantModifyDTO.getLegalEntityIdNumber());
        small.setRentMerchantAddress(address);
        small.setBusinessLicenseNumber(rentMerchantModifyDTO.getBusinessListenNumber());
        small.setBusinessLicenseFile(StringUtils.join(",", rentMerchantModifyDTO.getBusinessListen()));
        small.setHighestDegreeDiplomaFile(StringUtils.join(",", rentMerchantModifyDTO.getHighestDegreeDiploma()));
        small.setDrivingLicenseFile(StringUtils.join(",", rentMerchantModifyDTO.getDrivingLicense()));
        small.setHouseProprietaryCertificateFile(StringUtils.join(",", rentMerchantModifyDTO.getHouseProprietaryCertificate()));
        small.setProvinceId(rentMerchantModifyDTO.getProvinceId());
        small.setProvinceName(provinceName);
        small.setCityId(rentMerchantModifyDTO.getCityId());
        small.setCityName(cityName);
        small.setCountyId(rentMerchantModifyDTO.getCountyId());
        small.setCountyName(countyName);
        small.setConsigneeAddress(rentMerchantModifyDTO.getConsigneeAddress());
        userRentMerchantService.update(small);
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
