package com.newframe.controllers.api;

import com.newframe.common.anony.Anonymous;
import com.newframe.common.anony.UserType;
import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.dto.RequestUser;
import com.newframe.dto.user.request.*;
import com.newframe.dto.user.response.*;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.SystemCode;
import com.newframe.enums.TypeEnum;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.services.common.AliossService;
import com.newframe.services.user.RoleBaseService;
import com.newframe.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author:wangdong
 * @description:用户相关模块的Controller
 */
@RestController
@RequestMapping("/rent/api/user/")
public class ApiUserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleBaseService roleBaseService;
    @Autowired
    private AliossService aliossService;

    /**
     * @param mobile 手机号
     * @param mCode  验证码
     * @return
     * @description 注册
     * @author WangBin
     * @date 2018/8/8 14:31
     */
    @PostMapping("register")
    public JsonResult register(String mobile, String mCode) {
        OperationResult<UserBaseInfoDTO> result = userService.register(mobile, mCode);
        if (result.getEntity() == null) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * 更新token
     * @param uid
     * @param token
     * @return
     */
    @PostMapping("refreshToken")
    public JsonResult refreshToken(Long uid, String token){
        OperationResult<UserBaseInfoDTO> result = userService.refreshToken(uid, token);
        if (result.getEntity() == null) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param mobile 手机号
     * @return
     * @description 校验手机号和密码是否存在
     * @author WangBin
     * @date 2018/8/8 14:31
     */
    @PostMapping("checkMobileAndPasswordExists")
    public JsonResult checkMobileExists(String mobile) {
        OperationResult<UserRegisterDTO> result = userService.checkExistsMobileAndPassword(mobile);
        if (result.getEntity() == null) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    @PostMapping("passwordLogin")
    public JsonResult passwordLogin(String mobile, String password) {
        OperationResult<UserBaseInfoDTO> result = userService.passwordLogin(mobile, password);
        if (result.getEntity() == null) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param mobile   手机号
     * @param codeType 验证码类型
     * @return com.newframe.controllers.JsonResult
     * @description 发送验证码
     * @author WangBin
     * @date 2018/8/9 15:48
     */
    @PostMapping("sendVerificationCode")
    public JsonResult sendVerificationCode(String mobile, Integer codeType) {
        OperationResult<String> result = userService.sendVerificationCode(mobile, null);
        if (result.getEntity() == null) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param mobile
     * @param code
     * @return
     * @description 验证码登录
     * @author WangBin
     * @date 2018/8/9 16:05
     */
    @PostMapping("verificationCodeLogin")
    public JsonResult verificationCodeLogin(String mobile, String code) {
        OperationResult<UserBaseInfoDTO> result = userService.verificationCodeLogin(mobile, code);
        if (result.getEntity() == null) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * 注销登录
     * @return
     */
    @PostMapping("logout")
    public JsonResult logout(Long uid){
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        userService.logout(uid);
        return success(true);
    }

    /**
     * @param password        密码
     * @param confirmPassword 确认密码
     * @return
     * @description 设置密码登录状态
     * @author WangBin
     * @date 2018/8/9 16:08
     */
    @PostMapping("setPassword")
    public JsonResult setPassword(Long uid, String password, String confirmPassword) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = userService.setPassword(uid, password, confirmPassword);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param mobile   手机号
     * @param mCode    验证码
     * @param password 密码
     * @return
     * @description 未登录设置密码
     * @author WangBin
     * @date 2018/8/13 16:35
     */
    @PostMapping("setLoginPassword")
    public JsonResult setLoginPassword(String mobile, String mCode, String password) {
        OperationResult<Boolean> result = userService.setLoginPassword(mobile, mCode, password);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param oldPassword     原密码
     * @param newPassword     新密码
     * @param confirmPassword
     * @return
     * @description 修改密码
     * @author WangBin
     * @date 2018/8/9 16:10
     */
    @PostMapping("modifyPassword")
    public JsonResult modifyPassword(Long uid, String oldPassword, String newPassword, String confirmPassword) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = userService.modifyPassword(uid, oldPassword, newPassword, confirmPassword);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param mobile
     * @param mCode
     * @return
     * @description 忘记密码
     * @author WangBin
     * @date 2018/8/14 16:02
     */
    @PostMapping("forgetPassword")
    public JsonResult forgetPassword(String mobile, String mCode, String password) {
        Long uid = RequestUser.getCurrentUid();
        OperationResult<Boolean> result = userService.forgetPassword(mobile, mCode, password);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param newMobile  新手机号
     * @param mobileCode 手机验证码
     * @return
     * @description 修改手机号
     * @author WangBin
     * @date 2018/8/9 16:16
     */
    @PostMapping("modifyMobile")
    public JsonResult modifyMobile(Long uid, String newMobile, String mobileCode) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = userService.modifyMobile(uid, newMobile, mobileCode);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param mobileCode 注销手机号
     * @return
     * @description 注销手机号
     * @author WangBin
     * @date 2018/8/9 16:19
     */
    @PostMapping("removeMobile")
    public JsonResult removeMobile(Long uid, String mobileCode) {
//      Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = userService.removeMobile(uid, mobileCode);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param roleId 角色id
     * @return
     * @description 获取角色申请
     * @author WangBin
     * @date 2018/8/8 17:48
     */
    @PostMapping("getRoleApply")
    public JsonResult getRoleApply(Long uid, Integer roleId) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<UserRoleApplyDTO.RoleApplyResult> result = userService.getUserApply(uid);
        return success(result.getEntity());
    }

    /**
     * @description 撤销申请
     * @author WangBin
     * @param roleApplyId 角色申请id
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 10:29
     */
    @PostMapping("revokeRoleApply")
    public JsonResult revokeRoleApply(Long uid, Long roleApplyId){
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = roleBaseService.revokeRoleApply(uid, roleApplyId);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param rentMerchantApplyDTO
     * @return
     * @description 租赁商角色申请
     * @author WangBin
     * @date 2018/8/9 16:43
     */
    @PostMapping("rentMerchantRoleApply")
    public JsonResult rentMerchantRoleApply(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO) throws IOException {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.FIRST_RENT_MERCHANT.getRoleId();
        OperationResult<Boolean> result = roleBaseService.roleApply(uid, rentMerchantApplyDTO, roleId);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param roleApplyId 角色申请id
     * @return
     * @description 获取租赁商申请详细信息
     * @author WangBin
     * @date 2018/8/13 14:34
     */
    @PostMapping("getRentMechantApplyInfo")
    public JsonResult getRentMechantApplyInfo(Long uid, Long roleApplyId) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.FIRST_RENT_MERCHANT.getRoleId();
        OperationResult<UserRoleApplyDTO> result = roleBaseService.getUserApplyInfo(uid, roleId, roleApplyId);
        return success(result.getEntity());
    }

    /**
     * @description 获取租赁商信息
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 17:55
     */
    @PostMapping("getRentMerchantInfo")
    public JsonResult getRentMerchantInfo(Long uid){
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.FIRST_RENT_MERCHANT.getRoleId();
        OperationResult<UserRoleDTO> result = roleBaseService.getUserRoleInfo(uid, roleId);
        return success(result.getEntity());
    }

    /**
     * @description 设置指定供应商开关
     * @author WangBin
     * @param appoint
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 20:39
     */
    @PostMapping("setAppoint")
    public JsonResult setAppoint(Long uid, boolean appoint){
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.FIRST_RENT_MERCHANT.getRoleId();
        OperationResult<Boolean> result = roleBaseService.setAppoint(uid, roleId, appoint);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @description 指定供应商
     * @author WangBin
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 20:15
     */
    @PostMapping("appointSupplier")
    public JsonResult appointSupplier(Long uid, RentMerchantModifyDTO rentMerchantModifyDTO){
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.FIRST_RENT_MERCHANT.getRoleId();
        OperationResult<Boolean> result = roleBaseService.modifyAppointSupplier(uid, roleId,
                rentMerchantModifyDTO.getSupplierUid(),
                rentMerchantModifyDTO.getRevokeSupplierUid());
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @description 供应商指定查询
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 18:51
     */
    @PostMapping("getMerchantAppoint")
    public JsonResult getMerchantAppoint(Long uid){
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<AppointSupplierDTO> result = roleBaseService.getAppointSupplier(uid);
        if (result.getEntity() == null){
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param funderApplyDTO
     * @return
     * @description 资金方角色申请
     * @author WangBin
     * @date 2018/8/9 16:50
     */
    @PostMapping("funderRoleApply")
    public JsonResult funderRoleApply(Long uid, FunderApplyDTO funderApplyDTO) throws IOException{
//      Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.FUNDER.getRoleId();
        OperationResult<Boolean> result = roleBaseService.roleApply(uid, funderApplyDTO, roleId);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param roleApplyId 角色申请id
     * @return
     * @description 获取资金方角色申请详细信息
     * @author WangBin
     * @date 2018/8/13 14:37
     */
    @PostMapping("getFunderApplyInfo")
    public JsonResult getFunderApplyInfo(Long uid, Long roleApplyId) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.FUNDER.getRoleId();
        OperationResult<UserRoleApplyDTO> result = roleBaseService.getUserApplyInfo(uid, roleId, roleApplyId);
        if (result.getEntity() == null) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * 获取资金方线下放款资格
     * @param uid
     * @return
     */
    @PostMapping("getFunderQualification")
    public JsonResult getFunderQualification(Long uid){
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = userService.getFunderQualification(uid);
        if (result.getEntity() == null){
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @description 获取资金方信息
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 17:57
     */
    @PostMapping("getFunderInfo")
    public JsonResult getFunderInfo(Long uid){
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.FUNDER.getRoleId();
        OperationResult<UserRoleDTO> result = roleBaseService.getUserRoleInfo(uid, roleId);
        return success(result.getEntity());
    }

    /**
     * @param hirerApplyDTO
     * @return
     * @description 出租方申请
     * @author WangBin
     * @date 2018/8/9 16:52
     */
    @PostMapping("hirerRoleApply")
    public JsonResult hirerRoleApply(Long uid, HirerApplyDTO hirerApplyDTO) throws IOException{
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.HIRER.getRoleId();
        OperationResult<Boolean> result = roleBaseService.roleApply(uid, hirerApplyDTO, roleId);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param roleApplyId 角色申请id
     * @return
     * @description 获取出租方申请详细信息
     * @author WangBin
     * @date 2018/8/13 14:39
     */
    @PostMapping("getHirerApplyInfo")
    public JsonResult getHirerApplyInfo(Long uid, Long roleApplyId) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.HIRER.getRoleId();
        OperationResult<UserRoleApplyDTO> result = roleBaseService.getUserApplyInfo(uid, roleId, roleApplyId);
        return success(result.getEntity());
    }

    /**
     * @description 获取出租方信息
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 17:58
     */
    @PostMapping("getHirerInfo")
    public JsonResult getHirerInfo(Long uid){
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.HIRER.getRoleId();
        OperationResult<UserRoleDTO> result = roleBaseService.getUserRoleInfo(uid, roleId);
        return success(result.getEntity());
    }

    /**
     * @param supplierApplyDTO
     * @return
     * @description 供应商角色申请
     * @author WangBin
     * @date 2018/8/9 16:59
     */
    @PostMapping("supplierRoleApply")
    public JsonResult supplierRoleApply(Long uid, SupplierApplyDTO supplierApplyDTO) throws IOException {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.SUPPLIER.getRoleId();
        OperationResult<Boolean> result = roleBaseService.roleApply(uid, supplierApplyDTO, roleId);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param roleApplyId 角色申请id
     * @return
     * @description 获取供应商角色申请详细信息
     * @author WangBin
     * @date 2018/8/13 14:40
     */
    @PostMapping("getSupplierApplyInfo")
    public JsonResult getSupplierApplyInfo(Long uid, Long roleApplyId) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.SUPPLIER.getRoleId();
        OperationResult<UserRoleApplyDTO> result = roleBaseService.getUserApplyInfo(uid, roleId, roleApplyId);
        return success(result.getEntity());
    }

    /**
     * @description 获取供应商信息
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 18:00
     */
    @PostMapping("getSupplierInfo")
    public JsonResult getSupplierInfo(Long uid){
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.SUPPLIER.getRoleId();
        OperationResult<UserRoleDTO> result = roleBaseService.getUserRoleInfo(uid, roleId);
        return success(result.getEntity());
    }

    /**
     * @description  获取供应商列表
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 18:23
     */
    @PostMapping("getSupplierList")
    public JsonResult getSupplierList(Long uid){
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.SUPPLIER.getRoleId();
        OperationResult<List<UserRoleDTO.Supplier>> result = roleBaseService.getAllSupplier(roleId);
        return success(result.getEntity());
    }

    /**
     * @return
     * @description 获取地址列表
     * @author WangBin
     * @date 2018/8/8 14:35
     */
    @PostMapping("getAddress")
    public JsonResult getAddress(Long uid) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<List<UserAddressDTO>> result = userService.getUserAddressList(uid);
        return success(result.getEntity());
    }

    /**
     * @description 新增地址
     * @author WangBin
     * @date 2018/8/9 17:27
     */
    @PostMapping("addAddress")
    public JsonResult addAddress(Long uid, AddressDTO addressDTO) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = userService.addUserAddress(uid, addressDTO);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @description 修改地址
     * @author WangBin
     * @date 2018/8/9 17:34
     */
    @PostMapping("modifyAddress")
    public JsonResult modifyAddress(Long uid, AddressDTO addressDTO) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = userService.modifyAddress(uid, addressDTO);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param addressId 地址id
     * @return
     * @description 删除地址
     * @author WangBin
     * @date 2018/8/8 17:52
     */
    @PostMapping("removeAddress")
    public JsonResult removeAddress(Long uid, Long addressId) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = userService.removeAddress(uid, addressId);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param addressId 地址id
     * @return
     * @description 设置默认地址
     * @author WangBin
     * @date 2018/8/8 14:36
     */
    @PostMapping("setDefaultAddress")
    public JsonResult setDefaultAddress(Long uid, Long addressId) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = userService.setDefaultAddress(uid, addressId);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @return
     * @description 获取小B列表
     * @author WangBin
     * @date 2018/8/8 18:04
     */
    @PostMapping("getRentMerchantList")
    public JsonResult getRentMerchantList(Long uid) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.FIRST_RENT_MERCHANT.getRoleId();
        OperationResult<List<UserRoleDTO.SmallRentMechant>> result = roleBaseService.getSmallRentMechantList(uid, roleId);
        return success(result.getEntity());
    }

    /**
     * @return
     * @description 大B添加小B
     * @author WangBin
     * @date 2018/8/9 17:46
     */
    @PostMapping("addRentMerchant")
    public JsonResult addRentMerchant(Long uid, RentMerchantApplyDTO rentMerchantApplyDTO) throws IOException{
//        Long uid = RequestUser.getCurrentUid();
        System.out.println("添加小B进来了");
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = roleBaseService.addSmallRentMechant(uid, rentMerchantApplyDTO);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @description 修改小B的信息
     * @author WangBin
     * @date 2018/8/9 17:49
     */
    @PostMapping("modifyRentMerchant")
    public JsonResult modifyRentMerchant(Long uid, RentMerchantModifyDTO rentMerchantModifyDTO) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = roleBaseService.modifySmallRentMechant(uid, rentMerchantModifyDTO);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param removeUid 要删除的小B uid
     * @return
     * @description 删除小B
     * @author WangBin
     * @date 2018/8/8 18:00
     */
    @PostMapping("removeRentmerchant")
    public JsonResult removeRentmerchant(Long uid, Long removeUid) {
//        Long uid = RequestUser.getCurrentUid();
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        Integer roleId = RoleEnum.FIRST_RENT_MERCHANT.getRoleId();
        OperationResult<Boolean> result = roleBaseService.removeSmallRentMechant(uid, roleId, removeUid);
        if (!result.getEntity()) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * @param files 文件
     * @return
     * @description 上传文件
     * @author WangBin
     * @date 2018/8/15 10:46
     */
    @PostMapping("uploadFile")
    public JsonResult uploadFile(Long uid, List<MultipartFile> files) throws IOException{
        if (uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        String bucket = "fzmsupplychain";
        List<String> urls = aliossService.uploadFilesToBasetool(files, bucket);
        return success(urls);
    }
}
