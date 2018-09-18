package com.newframe.services.user;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.*;
import com.newframe.dto.user.response.*;
import com.newframe.entity.user.Area;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author WangBin
 */
public interface UserService {

    /**
     * @Description 发送验证码
     * @Author WangBin
     * @Param mobile 手机号
     * @Param codeType 邮箱
     * @Return
     * @Date 2018/8/14 15:05
     */
    OperationResult<String> sendVerificationCode(String mobile, Integer codeType);

    /**
      * @Description 注册
      * @Author WangBin
      * @param mobile 手机号
      * @param mCode
      * @return
      * @Date 2018/8/15 16:45
      **/
    OperationResult<UserBaseInfoDTO> register(String mobile, String mCode);

    /**
     * 刷新token
     * @param uid
     * @param token
     * @return
     */
    OperationResult<UserBaseInfoDTO> refreshToken(Long uid, String token);

    /**
     * @Description 密码登录
     * @Author WangBin
     * @Param mobile 手机号
     * @Param password 密码
     * @Return
     * @Date 2018/8/8 14:32
     */
    OperationResult<UserBaseInfoDTO> passwordLogin(String mobile, String password);

    /**
     * @description 验证码登录
     * @author WangBin
     * @param mobile
     * @param mCode
     * @return
     * @date 2018/8/15 16:50
     */
    OperationResult<UserBaseInfoDTO> verificationCodeLogin(String mobile, String mCode);

    /**
     * 注销登录
     * @param uid
     * @return
     */
    OperationResult<Boolean> logout(Long uid);

    /**
     * @Description 未登录设置密码
     * @Author WangBin
     * @Param mobile 手机号
     * @Param mCode 验证码
     * @Param password 密码
     * @Return
     * @Date 2018/8/13 16:35
     */
    OperationResult<Boolean> setLoginPassword(String mobile, String mCode, String password);

    /**
     * @Description 设置密码登录状态
     * @Author WangBin
     * @param uid 用户id
     * @Param password 密码
     * @Param confirmPassword 确认密码
     * @Return
     * @Date 2018/8/9 16:08
     */
    OperationResult<Boolean> setPassword(Long uid, String password, String confirmPassword);

    /**
     * @Description 修改密码
     * @Author WangBin
     * @param uid
     * @Param oldPassword 原密码
     * @Param newPassword 新密码
     * @Param confirmPassword
     * @Return
     * @Date 2018/8/9 16:10
     */
    OperationResult<Boolean> modifyPassword(Long uid, String oldPassword, String newPassword, String confirmPassword);

    /**
     * @Description 忘记密码
     * @Author WangBin
     * @Param mobile 手机号
     * @Param mCode 验证码
     * @Param password 密码
     * @Return
     * @Date 2018/8/14 15:24
     */
    OperationResult<Boolean> forgetPassword(String mobile, String mCode, String password);

    /**
     * @Description 修改手机号
     * @Author WangBin
     * @param uid
     * @Param newMobile 新手机号
     * @Param mobileCode 手机验证码
     * @Return
     * @Date 2018/8/9 16:16
     */
    OperationResult<Boolean> modifyMobile(Long uid, String newMobile, String mobileCode);

    /**
     * @Description 注销手机号
     * @Author WangBin
     * @param uid
     * @Param mobileCode 验证码
     * @Return
     * @Date 2018/8/9 16:19
     */
    OperationResult<Boolean> removeMobile(Long uid, String mobileCode);

    /**
     * @param mobile 手机号
     * @return
     * @description 校验手机号和密码是否存在
     * @author WangBin
     * @date 2018/8/8 14:31
     */
    OperationResult<UserRegisterDTO> checkExistsMobileAndPassword(String mobile);

    /**
     * 根据手机号修改密码
     * @param mobile
     * @param password 密码
     * @return
     */
    Boolean modifyPasswordByMobile(String mobile, String password);

    /**
     * 根据uid修改密码
     * @param uid
     * @param password 密码
     * @return
     */
    Boolean modifyPasswordByUid(Long uid, String password);

    /**
     * 根据uid获取用户的地址列表
     * @param uid
     * @return
     */
    OperationResult<List<UserAddressDTO>> getUserAddressList(Long uid);

    /**
     * 获取银行列表
     * @param uid
     * @return
     */
    OperationResult<UserBankDTO> getBankList(Long uid);

    /**
     * 添加或者修改银行卡
     * @param uid
     * @param bankDTO
     * @return
     */
    OperationResult<Boolean> saveBankNumber(Long uid, BankDTO bankDTO);

    /**
     * 获取资金流水记录
     * @param uid
     * @param type
     * @param condition
     * @return
     */
    OperationResult<BankFlowDTO> getAssetFlowRecord(Long uid, Integer type, PageSearchDTO condition);

    /**
     * 添加充值记录
     * @param bankNumber
     * @param amount
     * @return
     */
    OperationResult<Boolean> addRechargeRecord(String bankNumber, BigDecimal amount);

    /**
     * 添加提现记录
     * @param uid
     * @param amount
     * @return
     */
    OperationResult<Boolean> addDrawRecord(Long uid, BigDecimal amount);

    /**
     * 银行处理成功
     * @param bankMoneyFlowId
     * @return
     */
    OperationResult<Boolean> bankDrawByPass(Long bankMoneyFlowId);

    /**
     * 银行处理失败
     * @param bankMoneyFlowId
     * @return
     */
    OperationResult<Boolean> bankDrawByFail(Long bankMoneyFlowId);

    /**
     * 添加地址
     * @param uid
     * @param addressDTO
     * @return
     */
    OperationResult<Boolean> addUserAddress(Long uid, AddressDTO addressDTO);

    /**
     * 修改地址
     * @param uid
     * @param addressDTO
     * @return
     */
    OperationResult<Boolean> modifyAddress(Long uid, AddressDTO addressDTO);

    /**
     * 设置默认地址
     * @param uid
     * @param addressId
     * @return
     */
    OperationResult<Boolean> setDefaultAddress(Long uid, Long addressId);

    /**
     * 取消默认地址
     * @param uid
     * @return
     */
    OperationResult<Boolean> revokeDefaultAddress(Long uid);

    /**
     * 校验并获取地址
     *
     * @param provinceId
     * @param cityId
     * @param countyId
     * @return
     */
    List<Area> checkAddress(Integer provinceId, Integer cityId, Integer countyId);

    /**
     * 删除地址
     * @param uid
     * @param addressId
     * @return
     */
    OperationResult<Boolean> removeAddress(Long uid, Long addressId);

    /**
     * 更换手机号
     * @param uid
     * @param phoneNumber
     * @return
     */
    OperationResult<Boolean> modifyPhoneNumber(Long uid, String phoneNumber);

    /**
     * 获取角色申请记录
     * @param uid
     * @return
     */
    OperationResult<UserRoleApplyDTO.RoleApplyResult> getUserApply(Long uid);

    /**
     * 获取资金方线下放款资格
     * @param uid
     * @return
     */
    OperationResult<Boolean> getFunderQualification(Long uid);
}