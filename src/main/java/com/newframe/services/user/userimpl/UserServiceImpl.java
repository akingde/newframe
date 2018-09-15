package com.newframe.services.user.userimpl;

import com.google.common.collect.Lists;
import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.*;
import com.newframe.dto.user.response.*;
import com.newframe.entity.account.Account;
import com.newframe.entity.user.*;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.user.*;
import com.newframe.services.account.AccountManageService;
import com.newframe.services.user.RoleBaseService;
import com.newframe.services.user.SessionService;
import com.newframe.services.user.UserService;
import com.newframe.services.userbase.*;
import com.newframe.utils.BankCardUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private UserRoleService userRoleService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserRoleApplyService userRoleApplyService;
    @Autowired
    private UserFunderService userFunderService;
    @Autowired
    private RoleBaseService roleBaseService;
    @Autowired
    private UserBankService userBankService;
    @Autowired
    private AccountManageService accountManageService;
    @Autowired
    private CapitalFlowService capitalFlowService;

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
    public OperationResult<UserBaseInfoDTO> register(String mobile, String mCode) {
        if(!PatternEnum.checkPattern(mobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID);
        }
        if(mCode == null){
            return new OperationResult<>(RequestResultEnum.VERIFICATION_CODE_INVALID);
        }
        if(checkExistsMobileAndPassword(mobile).getEntity().getMobile()){
            return new OperationResult<>(RequestResultEnum.MOBILE_EXISTS);
        }
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setPhoneNumber(mobile);
        userBaseInfo.setUserStatus(UserStatusEnum.NORMAL.getUserStatus());
        UserBaseInfo baseInfo = userBaseInfoService.insert(userBaseInfo);
        UserPwd userPwd = new UserPwd();
        userPwd.setUid(baseInfo.getUid());
        userPwdService.insert(userPwd);
        accountManageService.saveAccount(userBaseInfo.getUid());
//        String appToken = sessionService.setAppUserToken(baseInfo.getUid());
//        String webToken = sessionService.setWebUserToken(baseInfo.getUid());
//        String token = isWeb ? webToken : appToken;
        return new OperationResult(new UserBaseInfoDTO(baseInfo.getUid(), UUID.randomUUID().toString(), mobile));
    }

    /**
     * 刷新token
     *
     * @param uid
     * @param token
     * @return
     */
    @Override
    public OperationResult<UserBaseInfoDTO> refreshToken(Long uid, String token) {
//        OperationResult<String> result = sessionService.modifyAppUserToken(uid, token);
//        if (result.getEntity() == null){
//            return new OperationResult(result.getErrorCode());
//        }
        UserBaseInfo baseInfo = userBaseInfoService.findOne(uid);
        if (baseInfo == null){
            return new OperationResult(RequestResultEnum.USER_NOT_EXISTS);
        }
        UserRole userRole = new UserRole();
        userRole.setUid(uid);
        List<UserRole> userRoles = userRoleService.findUserRole(userRole);
        String newToken =  UUID.randomUUID().toString();
        UserBaseInfoDTO.Role role = returnUserBaseInfo(uid);
        return new OperationResult(new UserBaseInfoDTO(uid, newToken, baseInfo.getPhoneNumber(), role));
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
    public OperationResult<UserBaseInfoDTO> passwordLogin(String mobile, String password) {
        if(!PatternEnum.checkPattern(mobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID);
        }
        if(StringUtils.isEmpty(password)){
            return new OperationResult<>(RequestResultEnum.PASSWORD_INVALID);
        }
        UserBaseInfo userBaseInfo = userBaseInfoService.findOne(mobile);
        if(userBaseInfo == null){
            return new OperationResult<>(RequestResultEnum.MOBILE_NOT_EXISTS);
        }
        UserPwd userPwd = userPwdService.findByUid(userBaseInfo.getUid());
        if(!password.equals(userPwd.getLoginPwd())){
            return new OperationResult<>(RequestResultEnum.LOGIN_ERROR);
        }
//        String token = modifyToken(userBaseInfo.getUid(), isWeb);
        String token = UUID.randomUUID().toString();
        UserBaseInfoDTO.Role role = returnUserBaseInfo(userBaseInfo.getUid());
        return new OperationResult(new UserBaseInfoDTO(userBaseInfo.getUid(), token, mobile, role));
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
    public OperationResult<UserBaseInfoDTO> verificationCodeLogin(String mobile, String mCode) {
        if(!PatternEnum.checkPattern(mobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID);
        }
        UserBaseInfo userBaseInfo = userBaseInfoService.findOne(mobile);
        if(userBaseInfo == null){
            return register(mobile, mCode);
        }
//        String token = modifyToken(userBaseInfo.getUid(), isWeb);
        String token = UUID.randomUUID().toString();
        UserBaseInfoDTO.Role role = returnUserBaseInfo(userBaseInfo.getUid());
        return new OperationResult(new UserBaseInfoDTO(userBaseInfo.getUid(), token, mobile, role));
    }

    /**
     * 注销登录
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<Boolean> logout(Long uid) {
//        if(isWeb){
//            sessionService.cleanWebUserToken(uid);
//        }else {
//            sessionService.cleanAppUserToken(uid);
//        }
        return new OperationResult(true);
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
        if(!PatternEnum.checkPattern(mobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID, false);
        }
        if(StringUtils.isEmpty(mCode)){
            return new OperationResult(RequestResultEnum.VERIFICATION_CODE_INVALID, false);
        }
        if(StringUtils.isEmpty(password) || password.length() < 6 && password.length() > 16){
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
        if (password.length() < 6 || password.length() > 16){
            return new OperationResult(RequestResultEnum.PASSWORD_INVALID, false);
        }
        if(!password.equals(confirmPassword)){
            return new OperationResult(RequestResultEnum.PASSWORD_NOT_AGREEMENT, false);
        }
        UserBaseInfo baseInfo = userBaseInfoService.findOne(uid);
        if (baseInfo == null){
            return new OperationResult(RequestResultEnum.USER_NOT_EXISTS, false);
        }
        UserPwd userPwd = userPwdService.findByUid(uid);
        if (StringUtils.isNotEmpty(userPwd.getLoginPwd())) {
            return new OperationResult(RequestResultEnum.PASSWORD_EXISTS, false);
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
        if (StringUtils.isAnyEmpty(oldPassword, newPassword, confirmPassword)) {
            return new OperationResult(RequestResultEnum.PASSWORD_INVALID, false);
        }
        if (newPassword.length() < 6 || newPassword.length() > 16){
            return new OperationResult(RequestResultEnum.PASSWORD_INVALID, false);
        }
        if(newPassword.equals(oldPassword)){
            return new OperationResult(RequestResultEnum.PASSWORD_AGREEMENT, false);
        }
        UserBaseInfo baseInfo = userBaseInfoService.findOne(uid);
        if (baseInfo == null){
            return new OperationResult(RequestResultEnum.USER_NOT_EXISTS);
        }
        UserPwd userPwd = userPwdService.findByUid(uid);
        if (StringUtils.isEmpty(userPwd.getLoginPwd())) {
            return new OperationResult(RequestResultEnum.PASSWORD_NOT_EXISTS, false);
        }
        if (!oldPassword.equals(userPwd.getLoginPwd())) {
            return new OperationResult(RequestResultEnum.PASSWORD_ERROR, false);
        }
        return new OperationResult(modifyPasswordByUid(uid, newPassword));
    }

    /**
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
    public OperationResult<Boolean> forgetPassword(String mobile, String mCode, String password) {
        if(!PatternEnum.checkPattern(mobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID, false);
        }
        if(StringUtils.isEmpty(mCode)){
            return new OperationResult(RequestResultEnum.VERIFICATION_CODE_INVALID, false);
        }
        if(StringUtils.isEmpty(password) || password.length() < 6 && password.length() > 16){
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
        if(!PatternEnum.checkPattern(newMobile, PatternEnum.mobile)){
            return new OperationResult<>(RequestResultEnum.MOBILE_INVALID, false);
        }
        OperationResult<Boolean> result = modifyPhoneNumber(uid, newMobile);
        if (!result.getEntity()){
            return result;
        }
        roleBaseService.modifyMobile(uid, newMobile);
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
        if(!PatternEnum.checkPattern(mobile, PatternEnum.mobile)){
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
        userRegisterDTO.setPassword(StringUtils.isEmpty(userPwd.getLoginPwd())? false: true);
        return new OperationResult<UserRegisterDTO>(userRegisterDTO);
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
     * @return
     */
    @Override
    public OperationResult<List<UserAddressDTO>> getUserAddressList(Long uid) {
        List<UserAddress> addresses = userAddressService.findUserAddressList(uid);
        List<UserAddressDTO> result = Lists.newArrayList();
        for (UserAddress address : addresses){
            result.add(new UserAddressDTO(address));
        }
        return new OperationResult(result);
    }

    /**
     * 添加或者修改银行卡
     *
     * @param bankDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> saveBankNumber(Long uid, BankDTO bankDTO) {

        if(StringUtils.isEmpty(bankDTO.getBankName())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if(StringUtils.isEmpty(bankDTO.getBankDetailedName())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if(!BankCardUtils.checkBankCard(bankDTO.getBankNumber())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        UserBaseInfo baseInfo = userBaseInfoService.findOne(uid);
        if(baseInfo == null){
            return null;
        }
        List<UserRole> roles = userRoleService.findAll(uid);
        if(CollectionUtils.isEmpty(roles)){
            return new OperationResult(RequestResultEnum.ROLE_EXCEPTION, false);
        }
        UserRole userRole = roles.get(0);
        OperationResult<UserRoleDTO> roleInfo = roleBaseService.getUserRoleInfo(userRole.getUid(), userRole.getRoleId());
        if(!StringUtils.equals(bankDTO.getUserBankName(), roleInfo.getEntity().getLegalEntity())){
            return new OperationResult(RequestResultEnum.PARAMETER_ERROR, false);
        }
        UserRoleDTO roleDTO = roleInfo.getEntity();
        UserBank userBank = userBankService.findOne(uid);
        if(userBank == null){
            userBankService.insert(new UserBank(uid, bankDTO, baseInfo.getPhoneNumber()));
        }else{
            userBankService.update(new UserBank(uid, bankDTO, baseInfo.getPhoneNumber()));
        }
        return new OperationResult(true);
    }

    /**
     * 添加充值记录
     *
     * @param uid
     * @param amount
     * @return
     */
    @Override
    public OperationResult<Boolean> addRechargeRecord(Long uid, BigDecimal amount) {
        UserBank userBank = userBankService.findOne(uid);
        if(userBank == null){

        }
        return addCapitalFlow(uid, userBank, amount, AssetTypeEnum.RECHARGE.getType());
    }

    /**
     * 添加提现记录
     *
     * @param uid
     * @param amount
     * @return
     */
    @Override
    public OperationResult<Boolean> addDrawRecord(Long uid, BigDecimal amount) {
        UserBank userBank = userBankService.findOne(uid);
        if(userBank == null){

        }
        Account account = new Account();
        if (account.getUseableAmount().compareTo(amount) == -1){

        }

        return addCapitalFlow(uid, userBank, amount, AssetTypeEnum.DRAW.getType());
    }

    /**
     * 添加流水
     * @param uid
     * @param amount
     * @param type
     * @return
     */
    public OperationResult<Boolean> addCapitalFlow(Long uid, UserBank userBank, BigDecimal amount, Integer type){
        UserRole userRole = userRoleService.findAll(uid).get(0);
        OperationResult<UserRoleDTO> roleInfo = roleBaseService.getUserRoleInfo(userRole.getUid(), userRole.getRoleId());
        UserRoleDTO roleDTO = roleInfo.getEntity();
        Integer status = type.equals(AssetTypeEnum.RECHARGE.getType())
                                ? AssetStatusEnum.BANK_PROCESSING.getOrderStatus()
                                : AssetStatusEnum.CHECKING.getOrderStatus();
        CapitalFlow capitalFlow = new CapitalFlow(uid, userBank, roleDTO, amount, status, type);
        capitalFlowService.insert(capitalFlow);
        return new OperationResult(true);
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
            return new OperationResult(RequestResultEnum.ADDRESS_NOT_EXISTS, false);
        }
        if(StringUtils.isEmpty(addressDTO.getConsigneeName())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if(StringUtils.isEmpty(addressDTO.getConsigneeAddress())){
            return new OperationResult(RequestResultEnum.PARAMETER_LOSS, false);
        }
        if(!PatternEnum.checkPattern(addressDTO.getMobile(), PatternEnum.mobile)){
            return new OperationResult(RequestResultEnum.MOBILE_INVALID, false);
        }
        UserAddress userAddress = new UserAddress(uid, addressDTO, areas);
        if (addressDTO.isDefaultAddress()){
            revokeDefaultAddress(uid);
        }
        userAddressService.insert(userAddress);
        return new OperationResult(true);
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
            setDefaultAddress(uid, userAddress.getId());
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
    public OperationResult<Boolean> setDefaultAddress(Long uid, Long addressId) {
        if (userAddressService.findAddress(addressId, uid) == null){
            return new OperationResult(RequestResultEnum.ADDRESS_NOT_EXISTS, false);
        }
        revokeDefaultAddress(uid);
        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddress.setDefaultAddress(true);
        userAddressService.updateByAddressId(userAddress);
        return new OperationResult(true);
    }

    /**
     * 取消默认地址
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<Boolean> revokeDefaultAddress(Long uid) {
        UserAddress defaultAddress = userAddressService.findDefaultAddress(uid);
        if (defaultAddress != null){
            defaultAddress.setDefaultAddress(false);
            userAddressService.updateByAddressId(defaultAddress);
        }
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
        List<Integer> areaCode = Lists.newArrayList();
        if (provinceId != null && cityId != null && countyId != null){
            if (countyId <= cityId || cityId <= provinceId ) {
                return null;
            }
            Integer[] areaCodes = new Integer[]{provinceId, cityId, countyId};
            areaCode.addAll(Arrays.asList(areaCodes));
        }else if(provinceId != null && cityId != null){
            if (cityId <= provinceId){
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
        if(areas == null || areas.size() != areaCode.size()){
            return null;
        }
        List<Area> list = areas.stream().sorted(Comparator.comparing(Area::getAreaCode)).collect(Collectors.toList());
        if(list.size() == 3){
            if(!list.get(1).getParentId().equals(list.get(0).getAreaId())){
                return null;
            }
            if(!list.get(2).getParentId().equals(list.get(1).getAreaId())){
                return null;
            }
        }
        if(list.size() == 2){
            if(!list.get(1).getParentId().equals(list.get(0).getAreaId())){
                return null;
            }
        }
        return areas;
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
     * 更换手机号
     *
     * @param uid
     * @param phoneNumber
     * @return
     */
    @Override
    public OperationResult<Boolean> modifyPhoneNumber(Long uid, String phoneNumber) {
        UserBaseInfo info = userBaseInfoService.findOne(phoneNumber);
        if(info != null && !info.getUid().equals(uid)){
            return new OperationResult(RequestResultEnum.MOBILE_EXISTS, false);
        }
        UserBaseInfo baseInfo = userBaseInfoService.findOne(uid);
        if (baseInfo == null){
            return new OperationResult(RequestResultEnum.USER_NOT_EXISTS, false);
        }
        if (phoneNumber.equals(baseInfo.getPhoneNumber())){
            return new OperationResult(true);
        }
        baseInfo.setPhoneNumber(phoneNumber);
        userBaseInfoService.updateByUid(baseInfo);
        return new OperationResult(true);
    }

    /**
     * 获取角色申请记录
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<UserRoleApplyDTO.RoleApplyResult> getUserApply(Long uid) {
        UserRoleApply roleApply = userRoleApplyService.findOne(uid);
        if (roleApply == null){
            return new OperationResult();
        }
        return new OperationResult(new UserRoleApplyDTO.RoleApplyResult(roleApply));
    }

    /**
     * 对token进行更新
     *
     * @param uid
     * @return
     */
    public String modifyToken(Long uid, boolean isWeb) {
        return isWeb ? sessionService.modifyWebUserToken(uid) : sessionService.modifyAppUserToken(uid);
    }

    private UserBaseInfoDTO.Role returnUserBaseInfo(Long uid){
        UserRole userRole = new UserRole();
        userRole.setUid(uid);
        List<UserRole> roleList = userRoleService.findUserRole(userRole);
        UserRoleApply roleApply = userRoleApplyService.findOne(uid);
        if (roleList == null || roleList.size() == 0){
            if(roleApply == null){
                return null;
            }else{
                return new UserBaseInfoDTO.Role(roleApply.getRoleId(), false);
            }
        }else if(roleList.size() == 1){
            if(roleList.get(0).getRoleId().equals(RoleEnum.SECOND_RENT_MERCHANT.getRoleId())){
                return new UserBaseInfoDTO.Role(RoleEnum.SECOND_RENT_MERCHANT.getRoleId(), true);
            } else{
                return new UserBaseInfoDTO.Role(RoleEnum.HIRER.getRoleId(), true);
            }
        }else{
            Set<Integer> roleIds = roleList.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
            if(roleIds.stream().anyMatch(x -> RoleEnum.FIRST_RENT_MERCHANT.getRoleId().equals(x))){
                return new UserBaseInfoDTO.Role(RoleEnum.FIRST_RENT_MERCHANT.getRoleId(), true);
            }else if(roleIds.stream().anyMatch(x -> RoleEnum.FUNDER.getRoleId().equals(x))){
                return new UserBaseInfoDTO.Role(RoleEnum.FUNDER.getRoleId(), true);
            } else{
                return new UserBaseInfoDTO.Role(RoleEnum.SUPPLIER.getRoleId(), true);
            }
        }
    }

    /**
     * 获取资金方线下放款资格
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<Boolean> getFunderQualification(Long uid) {
        UserFunder funder = userFunderService.findOne(uid);
        if (funder == null){
            return new OperationResult(RequestResultEnum.ROLE_NOT_EXEISTS);
        }
        return new OperationResult(funder.getIsWhite());
    }

    /**
     * 银行处理成功
     *
     * @param orderId
     * @return
     */
    @Override
    public OperationResult<Boolean> bankDrawByPass(Long orderId) {
        return null;
    }

    /**
     * 银行处理失败
     *
     * @param orderId
     * @return
     */
    @Override
    public OperationResult<Boolean> bankDrawByFail(Long orderId) {
        return null;
    }

    /**
     * 提取通过
     *
     * @param uid
     * @param amount
     * @return
     */
    @Override
    public OperationResult<Boolean> passDrawAssetCheck(Long uid, BigDecimal amount) {
        return null;
    }

    /**
     * 提取失败
     *
     * @param uid
     * @param amount
     * @return
     */
    @Override
    public OperationResult<Boolean> failDrawAssetCheck(Long uid, BigDecimal amount) {
        return null;
    }
}