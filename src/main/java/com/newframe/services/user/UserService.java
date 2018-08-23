package com.newframe.services.user;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.AddressDTO;
import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.dto.user.request.RentMerchantApplyDTO;
import com.newframe.dto.user.request.RoleApplyDTO;
import com.newframe.dto.user.response.*;
import com.newframe.entity.user.Area;

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
     * @param isWeb 是否是pc
      * @return
      * @Date 2018/8/15 16:45
      **/
    OperationResult<UserBaseInfoDTO> register(String mobile, String mCode, boolean isWeb);

    /**
     * @Description 密码登录
     * @Author WangBin
     * @Param mobile 手机号
     * @Param password 密码
     * @param isWeb 是否是pc
     * @Return
     * @Date 2018/8/8 14:32
     */
    OperationResult<UserBaseInfoDTO> passwordLogin(String mobile, String password, boolean isWeb);

    /**
     * @description 验证码登录
     * @author WangBin
     * @param mobile
     * @param mCode
     * @param isWeb 是否是pc
     * @return
     * @date 2018/8/15 16:50
     */
    OperationResult<UserBaseInfoDTO> verificationCodeLogin(String mobile, String mCode, boolean isWeb);

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
     * @param uid
     * @Param mobile 手机号
     * @Param mCode 验证码
     * @Param password 密码
     * @Return
     * @Date 2018/8/14 15:24
     */
    OperationResult<Boolean> forgetPassword(Long uid, String mobile, String mCode, String password);

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
     *  对token进行更新
     * @param uid
     * @return
     */
    String modifyToken(Long uid, boolean isWeb);

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
     * @param pageSearchDTO
     * @return
     */
    OperationResult<UserAddressDTO> getUserAddressList(Long uid, PageSearchDTO pageSearchDTO);

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
    OperationResult<Boolean> serDefaultAddress(Long uid, Long addressId);

    /**
     * 校验并获取地址
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
     * 角色申请
     * @param uid
     * @param role
     * @param roleId
     * @return
     */
    OperationResult<Boolean> roleApply(Long uid, RoleApplyDTO role, Integer roleId);

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
    OperationResult<Boolean> modifyAppointSupplier(Long uid, Integer roleId,Long[] supplierUid, Long[] revokeSupplierUid);

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
    OperationResult<Boolean> addSmallRentMechant(Long uid, Integer roleId, RentMerchantApplyDTO rentMerchantApplyDTO);

    /**
     * 修改小B
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    OperationResult<Boolean> modifySmallRentMechant(Long uid, Integer roleId, RentMerchantApplyDTO rentMerchantApplyDTO);

    /**
     * 删除小B
     * @param uid
     * @param rentMerchantApplyDTO
     * @return
     */
    OperationResult<Boolean> removeSmallRentMechant(Long uid, Integer roleId, RentMerchantApplyDTO rentMerchantApplyDTO);

    /**
     * 校验修改小B的权限
     * @param uid
     * @return
     */
    boolean checkModifySmallRentMechantAuthorization(Long uid);
}