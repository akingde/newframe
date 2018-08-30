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
import com.newframe.enums.TypeEnum;
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
@RequestMapping("/app/user/")
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
    @Anonymous(true)
    @PostMapping("register")
    public JsonResult register(String mobile, String mCode) {
        OperationResult<UserBaseInfoDTO> result = userService.register(mobile, mCode, true);
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
    @Anonymous(true)
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
    @Anonymous(true)
    @PostMapping("checkMobileAndPasswordExists")
    public JsonResult checkMobileExists(String mobile) {
        OperationResult<UserRegisterDTO> result = userService.checkExistsMobileAndPassword(mobile);
        if (result.getEntity() == null) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    @Anonymous(true)
    @PostMapping("passwordLogin")
    public JsonResult passwordLogin(String mobile, String password) {
        OperationResult<UserBaseInfoDTO> result = userService.passwordLogin(mobile, password, true);
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
    @Anonymous(true)
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
    @Anonymous(true)
    @PostMapping("verificationCodeLogin")
    public JsonResult verificationCodeLogin(String mobile, String code) {
        OperationResult<UserBaseInfoDTO> result = userService.verificationCodeLogin(mobile, code, true);
        if (result.getEntity() == null) {
            return error(result.getErrorCode());
        }
        return success(result.getEntity());
    }

    /**
     * 注销登录
     * @return
     */
    @UserType(type = TypeEnum.app)
    @PostMapping("logout")
    public JsonResult logout(){
        Long uid = RequestUser.getCurrentUid();
        userService.logout(uid, true);
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
    @UserType(type = TypeEnum.app)
    @PostMapping("setPassword")
    public JsonResult setPassword(String password, String confirmPassword) {
        Long uid = RequestUser.getCurrentUid();
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
    @Anonymous(true)
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
    @UserType(type = TypeEnum.app)
    @PostMapping("modifyPassword")
    public JsonResult modifyPassword(String oldPassword, String newPassword, String confirmPassword) {
        Long uid = RequestUser.getCurrentUid();
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
    @Anonymous(true)
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
    @UserType(type = TypeEnum.app)
    @PostMapping("modifyMobile")
    public JsonResult modifyMobile(String newMobile, String mobileCode) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("removeMobile")
    public JsonResult removeMobile(String mobileCode) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getRoleApply")
    public JsonResult getRoleApply(Integer roleId) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("revokeRoleApple")
    public JsonResult revokeRoleApple(Long roleApplyId){
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("rentMerchantRoleApply")
    public JsonResult rentMerchantRoleApply(RentMerchantApplyDTO rentMerchantApplyDTO) throws IOException {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getRentMechantApplyInfo")
    public JsonResult getRentMechantApplyInfo(Long roleApplyId) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getRentMerchantInfo")
    public JsonResult getRentMerchantInfo(){
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("setAppoint")
    public JsonResult setAppoint(boolean appoint){
        Long uid = RequestUser.getCurrentUid();
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
     * @param supplierUid
     * @param revokeSupplierUid
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 20:15
     */
    @UserType(type = TypeEnum.app)
    @PostMapping("appointSupplier")
    public JsonResult appointSupplier(Long[] supplierUid, Long[] revokeSupplierUid){
        Long uid = RequestUser.getCurrentUid();
        Integer roleId = RoleEnum.FIRST_RENT_MERCHANT.getRoleId();
        OperationResult<Boolean> result = roleBaseService.modifyAppointSupplier(uid, roleId, supplierUid, revokeSupplierUid);
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getMerchantAppoint")
    public JsonResult getMerchantAppoint(){
        Long uid = RequestUser.getCurrentUid();
        OperationResult<List<UserRoleDTO.Supplier>> result = roleBaseService.getAppointSupplier(uid);
        return success(result.getEntity());
    }

    /**
     * @param funderApplyDTO
     * @return
     * @description 资金方角色申请
     * @author WangBin
     * @date 2018/8/9 16:50
     */
    @UserType(type = TypeEnum.app)
    @PostMapping("funderRoleApply")
    public JsonResult funderRoleApply(FunderApplyDTO funderApplyDTO) throws IOException{
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getFunderApplyInfo")
    public JsonResult getFunderApplyInfo(Long roleApplyId) {
        Long uid = RequestUser.getCurrentUid();
        Integer roleId = RoleEnum.FUNDER.getRoleId();
        OperationResult<UserRoleApplyDTO> result = roleBaseService.getUserApplyInfo(uid, roleId, roleApplyId);
        if (result.getEntity() == null) {
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getFunderInfo")
    public JsonResult getFunderInfo(){
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("hirerRoleApply")
    public JsonResult hirerRoleApply(HirerApplyDTO hirerApplyDTO) throws IOException{
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getHirerApplyInfo")
    public JsonResult getHirerApplyInfo(Long roleApplyId) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getHirerInfo")
    public JsonResult getHirerInfo(){
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("supplierRoleApply")
    public JsonResult supplierRoleApply(SupplierApplyDTO supplierApplyDTO) throws IOException {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getSupplierApplyInfo")
    public JsonResult getSupplierApplyInfo(Long roleApplyId) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getSupplierInfo")
    public JsonResult getSupplierInfo(){
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getSupplierList")
    public JsonResult getSupplierList(){
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getAddress")
    public JsonResult getAddress() {
        Long uid = RequestUser.getCurrentUid();
        OperationResult<List<UserAddressDTO>> result = userService.getUserAddressList(uid);
        return success(result.getEntity());
    }

    /**
     * @description 新增地址
     * @author WangBin
     * @date 2018/8/9 17:27
     */
    @UserType(type = TypeEnum.app)
    @PostMapping("addAddress")
    public JsonResult addAddress(AddressDTO addressDTO) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("modifyAddress")
    public JsonResult modifyAddress(AddressDTO addressDTO) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("removeAddress")
    public JsonResult removeAddress(Long addressId) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("setDefaultAddress")
    public JsonResult setDefaultAddress(Long addressId) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("getRentMerchantList")
    public JsonResult getRentMerchantList() {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("addRentMerchant")
    public JsonResult addRentMerchant(RentMerchantApplyDTO rentMerchantApplyDTO) throws IOException{
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("modifyRentMerchant")
    public JsonResult modifyRentMerchant(RentMerchantModifyDTO rentMerchantModifyDTO) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("removeRentmerchant")
    public JsonResult removeRentmerchant(Long removeUid) {
        Long uid = RequestUser.getCurrentUid();
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
    @UserType(type = TypeEnum.app)
    @PostMapping("uploadFile")
    public JsonResult uploadFile(List<MultipartFile> files) throws IOException{
        String bucket = "fzmsupplychain";
        List<String> urls = aliossService.uploadFilesToBasetool(files, bucket);
        return success(urls);
    }
}
