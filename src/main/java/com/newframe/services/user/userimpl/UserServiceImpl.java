package com.newframe.services.user.userimpl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.AddressDTO;
import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.dto.user.request.RentMerchantApplyDTO;
import com.newframe.dto.user.request.RoleApplyDTO;
import com.newframe.dto.user.response.*;
import com.newframe.entity.user.*;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.user.PatternEnum;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.enums.user.RoleStatusEnum;
import com.newframe.enums.user.UserStatusEnum;
import com.newframe.services.user.RoleService;
import com.newframe.services.user.UserService;
import com.newframe.services.userbase.*;
import com.newframe.utils.FileUtils;
import com.newframe.utils.IdNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toSet;

/**
 * @author WangBin
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private UserPwdService userPwdService;
    @Autowired
    private UserWebTokenService userWebTokenService;
    @Autowired
    private UserAppTokenService userAppTokenService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserRoleApplyService userRoleApplyService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private UserRentMerchantService userRentMerchantService;

    private Map<Integer, RoleService> roleServiceMap = new HashMap<>();

    public UserServiceImpl(List<RoleService> roleServices) {
        for (RoleService roleService : roleServices) {
            roleServiceMap.put(roleService.getRoleId(), roleService);
        }
    }

    /**
     * @param mobile
     * @param codeType
     * @Description 发送验证码
     * @Author WangBin
     * @Param mobile 手机号
     * @Param codeType 邮箱
     * @Return
     * @Date 2018/8/14 15:05
     */
    @Override
    public OperationResult<String> sendVerificationCode(String mobile, Integer codeType) {
        String str = "0123456789";
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < 4; i ++){
            char ch=str.charAt(new Random().nextInt(str.length()));
            result.append(ch);
        }
        return new OperationResult<String>(result.toString());
    }

    /**
     * @param mobile 手机号
     * @param mCode
     * @return
     * @Description 注册
     * @Author WangBin
     * @Date 2018/8/15 16:45
     **/
    @Override
    public OperationResult<UserBaseInfoDTO> register(String mobile, String mCode, boolean isWeb) {
        if(PatternEnum.checkPattern(mobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID);
        }
        if(mCode == null){
            return new OperationResult<>(RequestResultEnum.VERIFICATION_CODE_INVALID);
        }
        if(!checkExistsMobileAndPassword(mobile).getEntity().getMobile()){
            return new OperationResult<>(RequestResultEnum.MOBILE_EXISTS);
        }
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setPhoneNumber(mobile);
        userBaseInfo.setUserStatus(UserStatusEnum.NORMAL.getUserStatus());
        UserBaseInfo baseInfo = userBaseInfoService.insert(userBaseInfo);
        UserPwd userPwd = new UserPwd();
        userPwd.setUid(baseInfo.getUid());
        userPwdService.insert(userPwd);
        UserAppToken appToken = userAppTokenService.insert(baseInfo.getUid());
        UserWebToken webToken = userWebTokenService.insert(baseInfo.getUid());
        String token = isWeb?webToken.getToken():appToken.getToken();
        return new OperationResult(new UserBaseInfoDTO(baseInfo.getUid(), token, UserStatusEnum.NORMAL.getUserStatus()));
    }

    /**
     * @param mobile
     * @param password
     * @Description 密码登录
     * @Author WangBin
     * @Param mobile 手机号
     * @Param password 密码
     * @Return
     * @Date 2018/8/8 14:32
     */
    @Override
    public OperationResult<UserBaseInfoDTO> passwordLogin(String mobile, String password, boolean isWeb) {
        if(PatternEnum.checkPattern(mobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID);
        }
        if(password == null){
            return new OperationResult<>(RequestResultEnum.PASSWORD_INVALID);
        }
        UserBaseInfo userBaseInfo = userBaseInfoService.findOne(mobile);
        if(userBaseInfo == null){
            return new OperationResult<>(RequestResultEnum.MOBILE_NOT_EXISTS);
        }
        UserPwd userPwd = userPwdService.findByUid(userBaseInfo.getUid());
        if(password.equals(userPwd.getLoginPwd())){
            return new OperationResult<>(RequestResultEnum.LOGIN_ERROR);
        }
        String token = modifyToken(userBaseInfo.getUid(), isWeb);
        UserRole userRole = new UserRole();
        userRole.setUid(userBaseInfo.getUid());
        List<UserRole> userRoles = userRoleService.findUserRole(userRole);
        return new OperationResult(new UserBaseInfoDTO(userBaseInfo.getUid(), token, userBaseInfo.getUserStatus(), userRoles));
    }

    /**
     * @param mobile
     * @param mCode
     * @return
     * @description 验证码登录
     * @author WangBin
     * @date 2018/8/15 16:50
     */
    @Override
    public OperationResult<UserBaseInfoDTO> verificationCodeLogin(String mobile, String mCode, boolean isWeb) {
        if(PatternEnum.checkPattern(mobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID);
        }
        UserBaseInfo userBaseInfo = userBaseInfoService.findOne(mobile);
        if(userBaseInfo == null){
            return register(mobile, mCode, isWeb);
        }
        String token = modifyToken(userBaseInfo.getUid(), isWeb);
        UserRole userRole = new UserRole();
        userRole.setUid(userBaseInfo.getUid());
        List<UserRole> userRoles = userRoleService.findUserRole(userRole);
        return new OperationResult(new UserBaseInfoDTO(userBaseInfo.getUid(), token, userBaseInfo.getUserStatus(), userRoles));
    }

    /**
     * @param mobile
     * @param mCode
     * @param password
     * @Description 未登录设置密码
     * @Author WangBin
     * @Param mobile 手机号
     * @Param mCode 验证码
     * @Param password 密码
     * @Return
     * @Date 2018/8/13 16:35
     */
    @Override
    public OperationResult<Boolean> setLoginPassword(String mobile, String mCode, String password) {
        if(PatternEnum.checkPattern(mobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID, false);
        }
        if(mCode == null){
            return new OperationResult(RequestResultEnum.VERIFICATION_CODE_INVALID, false);
        }
        if(password == null || password.length() < 6 && password.length() > 16){
            return new OperationResult<>(RequestResultEnum.PASSWORD_INVALID, false);
        }
        UserRegisterDTO registerDTO = checkExistsMobileAndPassword(mobile).getEntity();
        if(!registerDTO.getMobile()){
            return new OperationResult(RequestResultEnum.MOBILE_NOT_EXISTS, false);
        }
        if(registerDTO.getPassword()){
            return new OperationResult(RequestResultEnum.PASSWORD_EXISTS, false);
        }
        return new OperationResult(modifyPasswordByMobile(mobile, password));
    }

    /**
     * @param uid             用户id
     * @param password
     * @param confirmPassword
     * @Description 设置密码登录状态
     * @Author WangBin
     * @Param password 密码
     * @Param confirmPassword 确认密码
     * @Return
     * @Date 2018/8/9 16:08
     */
    @Override
    public OperationResult<Boolean> setPassword(Long uid, String password, String confirmPassword) {
        if (confirmPassword == null || password == null) {
            return new OperationResult(RequestResultEnum.PASSWORD_INVALID, false);
        }
        UserPwd userPwd = userPwdService.findByUid(uid);
        if (userPwd.getLoginPwd() == null) {
            return new OperationResult(RequestResultEnum.PASSWORD_NOT_EXISTS, false);
        }
        if (password.length() < 6 || password.length() > 16){
            return new OperationResult(RequestResultEnum.PASSWORD_INVALID, false);
        }
        return new OperationResult(modifyPasswordByUid(uid, password));
    }

    /**
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @Description 修改密码
     * @Author WangBin
     * @Param oldPassword 原密码
     * @Param newPassword 新密码
     * @Param confirmPassword
     * @Return
     * @Date 2018/8/9 16:10
     */
    @Override
    public OperationResult<Boolean> modifyPassword(Long uid, String oldPassword, String newPassword, String confirmPassword) {
        if (oldPassword == null || confirmPassword == null || newPassword == null) {
            return new OperationResult(RequestResultEnum.PASSWORD_INVALID, false);
        }
        UserPwd userPwd = userPwdService.findByUid(uid);
        if (userPwd.getLoginPwd() == null) {
            return new OperationResult(RequestResultEnum.PASSWORD_NOT_EXISTS, false);
        }
        if (oldPassword.equals(userPwd.getLoginPwd()) || oldPassword.equals(confirmPassword)) {
            return new OperationResult(RequestResultEnum.PASSWORD_ERROR, false);
        }
        if (newPassword.length() < 6 || newPassword.length() > 16){
            return new OperationResult(RequestResultEnum.PASSWORD_INVALID, false);
        }
        if(newPassword.equals(oldPassword)){
            return new OperationResult(RequestResultEnum.PASSWORD_AGREEMENT, false);
        }
        return new OperationResult(modifyPasswordByUid(uid, newPassword));
    }

    /**
     * @param uid
     * @param mobile
     * @param mCode
     * @param password
     * @Description 忘记密码
     * @Author WangBin
     * @Param mobile 手机号
     * @Param code 验证码
     * @Param password 密码
     * @Return
     * @Date 2018/8/14 15:24
     */
    @Override
    public OperationResult<Boolean> forgetPassword(Long uid, String mobile, String mCode, String password) {
        if(PatternEnum.checkPattern(mobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID, false);
        }
        if(mCode == null){
            return new OperationResult(RequestResultEnum.VERIFICATION_CODE_INVALID, false);
        }
        if(password == null || password.length() < 6 && password.length() > 16){
            return new OperationResult<>(RequestResultEnum.PASSWORD_INVALID, false);
        }
        UserBaseInfo userBaseInfo = userBaseInfoService.findOne(mobile);
        if(userBaseInfo == null){
            return new OperationResult(RequestResultEnum.USER_NOT_EXISTS, false);
        }
        return new OperationResult(modifyPasswordByMobile(mobile, password));
    }

    /**
     * @param uid
     * @param newMobile
     * @param mobileCode
     * @Description 修改手机号
     * @Author WangBin
     * @Param newMobile 新手机号
     * @Param mobileCode 手机验证码
     * @Return
     * @Date 2018/8/9 16:16
     */
    @Override
    public OperationResult<Boolean> modifyMobile(Long uid, String newMobile, String mobileCode) {
        if(PatternEnum.checkPattern(newMobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID, false);
        }
        if(mobileCode == null){
            return new OperationResult(RequestResultEnum.VERIFICATION_CODE_INVALID, false);
        }
        UserBaseInfo info = userBaseInfoService.findOne(uid);
        if(info.getPhoneNumber().equals(newMobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID, false);
        }
        UserBaseInfo baseInfo = userBaseInfoService.findOne(newMobile);
        if (baseInfo != null){
            return new OperationResult(RequestResultEnum.MOBILE_EXISTS, false);
        }
        info.setPhoneNumber(newMobile);
        userBaseInfoService.updateByUid(info);
        return new OperationResult(true);
    }

    /**
     * @param uid
     * @param mobileCode
     * @Description 注销手机号
     * @Author WangBin
     * @Param mobileCode 验证码
     * @Return
     * @Date 2018/8/9 16:19
     */
    @Override
    public OperationResult<Boolean> removeMobile(Long uid, String mobileCode) {
        if(mobileCode == null){
            return new OperationResult(RequestResultEnum.VERIFICATION_CODE_INVALID, false);
        }
        userBaseInfoService.removeByUid(uid);
        return new OperationResult(true);
    }

    /**
     * @param mobile 手机号
     * @return
     * @description 校验手机号和密码是否存在
     * @author WangBin
     * @date 2018/8/8 14:31
     */
    @Override
    public OperationResult<UserRegisterDTO> checkExistsMobileAndPassword(String mobile) {
        if(!mobile.matches(PatternEnum.mobile.getPattern())){
            return new OperationResult<UserRegisterDTO>(RequestResultEnum.MOBILE_INVALID);
        }
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        UserBaseInfo userBaseInfo = userBaseInfoService.findOne(mobile);
        if (userBaseInfo == null ){
            userRegisterDTO.setMobile(false);
            userRegisterDTO.setPassword(false);
            return new OperationResult<UserRegisterDTO>(userRegisterDTO);
        }
        UserPwd userPwd = userPwdService.findByUid(userBaseInfo.getUid());
        userRegisterDTO.setMobile(true);
        userRegisterDTO.setPassword(userPwd == null ? false: true);
        return new OperationResult<UserRegisterDTO>(userRegisterDTO);
    }

    /**
     * 对token进行更新
     *
     * @param uid
     * @param isWeb
     * @return
     */
    @Override
    public String modifyToken(Long uid, boolean isWeb) {
        if(isWeb) {
            userWebTokenService.updateByUid(uid, UUID.randomUUID().toString());
            return userWebTokenService.findOne(uid).getToken();
        }else{
            userAppTokenService.updateByUid(uid, UUID.randomUUID().toString());
            return userAppTokenService.findOne(uid).getToken();
        }
    }

    /**
     * 根据手机号修改密码
     *
     * @param mobile
     * @param password 密码
     * @return
     */
    @Override
    public Boolean modifyPasswordByMobile(String mobile, String password) {
        UserBaseInfo userBaseInfo = userBaseInfoService.findOne(mobile);
        return modifyPasswordByUid(userBaseInfo.getUid(), password);
    }

    /**
     * 根据uid修改密码
     *
     * @param uid
     * @param password 密码
     * @return
     */
    @Override
    public Boolean modifyPasswordByUid(Long uid, String password) {
        UserPwd userPwd = new UserPwd();
        userPwd.setUid(uid);
        userPwd.setLoginPwd(password);
        userPwdService.updateByUid(userPwd);
        return true;
    }

    /**
     * 根据uid获取用户的地址列表
     *
     * @param uid
     * @param pageSearchDTO
     * @return
     */
    @Override
    public OperationResult<UserAddressDTO> getUserAddressList(Long uid, PageSearchDTO pageSearchDTO) {
        Page<UserAddress> page = userAddressService.findUserAddressList(uid, pageSearchDTO);
        return new OperationResult(new UserAddressDTO(page));
    }

    /**
     * 添加地址
     *
     * @param uid
     * @param addressDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> addUserAddress(Long uid, AddressDTO addressDTO) {
        List<Area> areas = checkAddress(addressDTO.getProvinceId(), addressDTO.getCityId(), addressDTO.getCountyId());
        if (areas == null){
            return new OperationResult<Boolean>(false);
        }
        UserAddress userAddress = new UserAddress(uid, addressDTO, areas);
        userAddressService.insert(userAddress);
        return new OperationResult<Boolean>(true);
    }

    /**
     * 修改地址
     *
     * @param uid
     * @param addressDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> modifyAddress(Long uid, AddressDTO addressDTO) {
        if (userAddressService.findAddress(addressDTO.getAddressId(), uid) == null){
            return new OperationResult(RequestResultEnum.ADDRESS_NOT_EXISTS, false);
        }
        List<Area> areas = checkAddress(addressDTO.getProvinceId(), addressDTO.getCityId(), addressDTO.getCountyId());
        if (areas == null){
            return new OperationResult(RequestResultEnum.ADDRESS_ERROR,false);
        }
        UserAddress userAddress = new UserAddress(uid, addressDTO, areas);
        if (addressDTO.isDefaultAddress()){
            serDefaultAddress(uid, userAddress.getId());
        }
        userAddressService.updateByAddressId(userAddress);
        return new OperationResult<Boolean>(true);
    }

    /**
     * 设置默认地址
     *
     * @param uid
     * @param addressId
     * @return
     */
    @Override
    public OperationResult<Boolean> serDefaultAddress(Long uid, Long addressId) {
        if (userAddressService.findAddress(addressId, uid) == null){
            return new OperationResult(RequestResultEnum.ADDRESS_NOT_EXISTS, false);
        }
        UserAddress defaultAddress = userAddressService.findDefaultAddress(uid);
        if (defaultAddress != null && defaultAddress.getDefaultAddress()){
            return new OperationResult(true);
        }
        UserAddress userAddress = new UserAddress();
        if (userAddress != null) {
            userAddress.setId(defaultAddress.getId());
            userAddress.setDefaultAddress(false);
            userAddressService.updateByAddressId(userAddress);
        }
        userAddress.setId(addressId);
        userAddress.setDefaultAddress(true);
        userAddressService.updateByAddressId(userAddress);
        return new OperationResult(true);
    }

    /**
     * 校验并获取地址
     *
     * @param provinceId
     * @param cityId
     * @param countyId
     * @return
     */
    @Override
    public List<Area> checkAddress(Integer provinceId, Integer cityId, Integer countyId) {
        List<Integer> areaCode = Collections.emptyList();
        if (provinceId != null && cityId != null && countyId != null){
            if (countyId > cityId || cityId > provinceId ) {
                return null;
            }
            Integer[] areaCodes = new Integer[]{provinceId, cityId, countyId};
            areaCode.addAll(Arrays.asList(areaCodes));
        }else if(provinceId != null && cityId != null){
            if (cityId > provinceId){
                return null;
            }
            Integer[] areaCodes = new Integer[]{provinceId, cityId};
            areaCode.addAll(Arrays.asList(areaCodes));
        } else {
            if(provinceId == null || provinceId % 10000 != 0){
                return null;
            }
            areaCode.add(provinceId);
        }
        List<Area> areas = areaService.findAreaByAreaCode(areaCode);
        return areas.size() == areaCode.size()?areas:null;
    }

    /**
     * 删除地址
     *
     * @param uid
     * @param addressId
     * @return
     */
    @Override
    public OperationResult<Boolean> removeAddress(Long uid, Long addressId) {
        if (userAddressService.findAddress(addressId, uid) == null){
            return new OperationResult(RequestResultEnum.ADDRESS_NOT_EXISTS, false);
        }
        userAddressService.removeByAddressId(addressId);
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
    public OperationResult<Boolean> roleApply(Long uid, RoleApplyDTO role, Integer roleId) {
        List<UserRoleApply> applyList = userRoleApplyService.findApplyList(uid);
        if(applyList != null || applyList.size() > 0){
            return new OperationResult(RequestResultEnum.ROLE_APPLY_TOO_MUCH, false);
        }
        if(StringUtils.isAnyEmpty(role.getName(), role.getLegalEntity(), role.getBusinessListenNumber())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if(!IdNumberUtils.isValidatedAllIdcard(role.getLegalEntityIdNumber())){
            return new OperationResult(RequestResultEnum.ID_NUMBER_ERROR, false);
        }
        if (FileUtils.isEmpty(role.getBusinessListen()) || role.getBusinessListen().length > 2) {
            return new OperationResult(RequestResultEnum.ILLEGAL_FILE, false);
        }
        if(!FileUtils.checkImage(role.getBusinessListen())){
            return new OperationResult(RequestResultEnum.FILE_FORMAT_ERROR, false);
        }
        return roleServiceMap.get(roleId).roleApply(uid, role);
    }

    /**
     * 获取角色申请详细信息
     * @param uid
     * @param roleId
     * @param roleApplyId
     * @return
     */
    public OperationResult<UserRoleApplyDTO> getUserRoleApplyInfo(Long uid, Integer roleId, Long roleApplyId){
        return roleServiceMap.get(roleId).getUserRoleApplyInfo(uid, roleApplyId);
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
     * 获取角色申请详细信息
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
    public OperationResult<List<UserRoleDTO.Supplier>> getAppointSupplier(Long uid) {
        return roleServiceMap.get(RoleEnum.SUPPLIER.getRoleId())
                                .getAppointSupplier(
                                        roleServiceMap.get(RoleEnum.FIRST_RENT_MERCHANT.getRoleId())
                                        .getAppointSupplierUid(uid)
                                );
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
        return roleServiceMap.get(roleId)
                            .setAppoint(uid, appoint);
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
    public OperationResult<Boolean> modifyAppointSupplier(Long uid, Integer roleId, Long[] supplierUid,
                                                          Long[] revokeSupplierUid) {
        Set<Long> inSet = Arrays.asList(supplierUid).stream().distinct().collect(toSet());
        Set<Long> reSet = Arrays.asList(revokeSupplierUid).stream().distinct().collect(toSet());
        long count = inSet.stream().filter(item -> reSet.contains(item)).count();
        if(supplierUid.length + revokeSupplierUid.length != inSet.size() + reSet.size() || count > 0){
            return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
        }
        UserRole userRole = userRoleService.findOne(uid, roleId, RoleStatusEnum.NORMAL.getRoleStatue());
        if (userRole == null){
            return new OperationResult(RequestResultEnum.ROLE_NOT_EXEISTS, false);
        }
        if(!userRentMerchantService.findOne(uid, roleId).getAppoint()){
            return new OperationResult(RequestResultEnum.INVALID_ACCESS, false);
        }
        List<MerchantAppoint> insertList = roleServiceMap.get(roleId).getAppointSupplier(uid, supplierUid);
        List<MerchantAppoint> removeList = roleServiceMap.get(roleId).getAppointSupplier(uid, revokeSupplierUid);
        if (inSet.size() != insertList.size() || reSet.size() != removeList.size()){
            return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
        }
        roleServiceMap.get(roleId).removeAppointSupplier(removeList);
        roleServiceMap.get(roleId).batchInsert(uid, supplierUid);
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
    public OperationResult<List<UserRoleDTO.SmallRentMechant>> getSmallRentMechantList(Long uid, Integer roleId) {
        return roleServiceMap.get(roleId).getSmallRentMechantList(uid);
    }

    /**
     * 新增小B
     *
     * @param uid
     * @param roleId
     * @param rentMerchantApplyDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> addSmallRentMechant(Long uid, Integer roleId, RentMerchantApplyDTO rentMerchantApplyDTO) {
        return null;
    }

    /**
     * 修改小B
     *
     * @param uid
     * @param roleId
     * @param rentMerchantApplyDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> modifySmallRentMechant(Long uid, Integer roleId, RentMerchantApplyDTO rentMerchantApplyDTO) {
        return null;
    }

    /**
     * 删除小B
     *
     * @param uid
     * @param roleId
     * @param rentMerchantApplyDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> removeSmallRentMechant(Long uid, Integer roleId, RentMerchantApplyDTO rentMerchantApplyDTO) {
        return null;
    }

    /**
     * 校验修改小B的权限
     *
     * @param uid
     * @return
     */
    @Override
    public boolean checkModifySmallRentMechantAuthorization(Long uid) {
        UserRentMerchant rentMerchant = userRentMerchantService.findOne(uid, RoleEnum.FIRST_RENT_MERCHANT.getRoleId());
        if (rentMerchant == null) {
            return false;
        }
        return true;
    }
}