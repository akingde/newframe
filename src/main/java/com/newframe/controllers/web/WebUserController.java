package com.newframe.controllers.web;

import com.newframe.common.anony.Anonymous;
import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.user.request.*;
import com.newframe.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author:wangdong
 * @description:用户相关模块的Controller
 */
@RestController
@RequestMapping("/app/user/")
public class WebUserController extends BaseController {

    @Autowired
    private UserService userService;

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
        /*
            1.手机号合法性校验和验证码校验
            2.判断手机号是否注册过
         */
        return null;
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
        /*
            1.手机号合法性校验
            2.判断手机号是否存在
         */
        return null;
    }

    @Anonymous(true)
    @PostMapping("passwordLogin")
    public JsonResult passwordLogin(String mobile, String password) {
        /*
            1.手机号和密码合法性校验
            2.密码是否正确
            3.生成新的token 和 查询出用户的信息返回给前端
         */
        return null;
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
        /*
            1.判断手机号是否合法 和 验证码类型是非正确
            2.发送验证码
         */
        return null;
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
        /*
            1.校验手机号的合法性
            2.对手机号和验证码 进行校验
            3.登录成功 生成新的 token，和 查询新的用户信息并返回
         */
        return null;
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
    public JsonResult setPassword(String password, String confirmPassword) {
        /*
            1.判断密码和确认密码 进行合法性验证
            2.判断用户是否有密码
         */
        return null;
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
        return null;
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
    public JsonResult modifyPassword(String oldPassword, String newPassword, String confirmPassword) {
        return null;
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
        return null;
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
    public JsonResult modifyMobile(String newMobile, String mobileCode) {
        return null;
    }

    /**
     * @param mobileCode 验证码
     * @return
     * @description 冻结手机号
     * @author WangBin
     * @date 2018/8/9 16:17
     */
    @PostMapping("freezeMobile")
    public JsonResult freezeMobile(String mobile, String mobileCode) {
        return null;
    }

    /**
     * @param mobileCode 注销手机号
     * @return
     * @description 注销手机号
     * @author WangBin
     * @date 2018/8/9 16:19
     */
    @PostMapping("removeMobile")
    public JsonResult removeMobile(String mobileCode) {
        return null;
    }

    /**
     * @param roleId 角色id
     * @return
     * @description 获取角色申请列表
     * @author WangBin
     * @date 2018/8/8 17:48
     */
    @PostMapping("getRoleApply")
    public JsonResult getRoleApply(Integer roleId) {
        return null;
    }

    /**
     * @description 撤销申请
     * @author WangBin
     * @param roleApplyId 角色申请id
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 10:29
     */
    @PostMapping("revokeRoleApple")
    public JsonResult revokeRoleApple(Long roleApplyId){
        return null;
    }

    /**
     * @param rentMerchantApplyDTO
     * @return
     * @description 租赁商角色申请
     * @author WangBin
     * @date 2018/8/9 16:43
     */
    @PostMapping("rentMerchantRoleApply")
    public JsonResult rentMerchantRoleApply(RentMerchantApplyDTO rentMerchantApplyDTO) {
        return null;
    }

    /**
     * @param roleApplyId 角色申请id
     * @return
     * @description 获取租赁商申请详细信息
     * @author WangBin
     * @date 2018/8/13 14:34
     */
    @PostMapping("getRentMechantApplyInfo")
    public JsonResult getRentMechantApplyInfo(Long roleApplyId) {
        return null;
    }

    /**
     * @description 获取租赁商信息
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 17:55
     */
    @PostMapping("getRentMerchantInfo")
    public JsonResult getRentMerchantInfo(){
        return null;
    }

    /**
     * @description 设置指定供应商开关
     * @author WangBin
     * @param appoint
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 20:39
     */
    @PostMapping("setAppoint")
    public JsonResult setAppoint(boolean appoint){
        return null;
    }

    /**
     * @description 指定供应商
     * @author WangBin
     * @param supplierUid
     * @param revokeSupplierUid
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 20:15
     */
    @PostMapping("appointSupplier")
    public JsonResult appointSupplier(Long[] supplierUid, Long[] revokeSupplierUid){
        return null;
    }

    /**
     * @description 供应商指定查询
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 18:51
     */
    @PostMapping("getMerchantAppoint")
    public JsonResult getMerchantAppoint(){
        return null;
    }

    /**
     * @param funderApplyDTO
     * @return
     * @description 资金方角色申请
     * @author WangBin
     * @date 2018/8/9 16:50
     */
    @PostMapping("funderRoleApply")
    public JsonResult funderRoleApply(FunderApplyDTO funderApplyDTO) {
        return null;
    }

    /**
     * @param roleApplyId 角色申请id
     * @return
     * @description 获取资金方角色申请详细信息
     * @author WangBin
     * @date 2018/8/13 14:37
     */
    @PostMapping("getFunderApplyInfo")
    public JsonResult getFunderApplyInfo(Long roleApplyId) {
        return null;
    }

    /**
     * @description 获取资金方信息
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 17:57
     */
    @PostMapping("getFunderInfo")
    public JsonResult getFunderInfo(){
        return null;
    }

    /**
     * @param hirerApplyDTO
     * @return
     * @description 出租方申请
     * @author WangBin
     * @date 2018/8/9 16:52
     */
    @PostMapping("hirerRoleApply")
    public JsonResult hirerRoleApply(HirerApplyDTO hirerApplyDTO) {
        return null;
    }

    /**
     * @param roleApplyId 角色申请id
     * @return
     * @description 获取出租方申请详细信息
     * @author WangBin
     * @date 2018/8/13 14:39
     */
    @PostMapping("getHirerApplyInfo")
    public JsonResult getHirerApplyInfo(Long roleApplyId) {
        return null;
    }

    /**
     * @description 获取出租方信息
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 17:58
     */
    @PostMapping("getHirerInfo")
    public JsonResult getHirerInfo(){
        return null;
    }

    /**
     * @param supplierApplyDTO
     * @return
     * @description 供应商角色申请
     * @author WangBin
     * @date 2018/8/9 16:59
     */
    @PostMapping("supplierRoleApply")
    public JsonResult supplierRoleApply(SupplierApplyDTO supplierApplyDTO) {
        return null;
    }

    /**
     * @param roleApplyId 角色申请id
     * @return
     * @description 获取供应商角色申请详细信息
     * @author WangBin
     * @date 2018/8/13 14:40
     */
    @PostMapping("getSupplierApplyInfo")
    public JsonResult getSupplierApplyInfo(Long roleApplyId) {
        return null;
    }

    /**
     * @description 获取供应商信息
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 18:00
     */
    @PostMapping("getSupplierInfo")
    public JsonResult getSupplierInfo(){
        return null;
    }

    /**
     * @description  获取供应商列表
     * @author WangBin
     * @param
     * @return com.newframe.controllers.JsonResult
     * @date 2018/8/16 18:23
     */
    @PostMapping("getSupplierList")
    public JsonResult getSupplierList(){
        return null;
    }

    /**
     * @param roleId 角色id
     * @return
     * @description 冻结角色
     * @author WangBin
     * @date 2018/8/8 17:55
     */
    @PostMapping("freezeRole")
    public JsonResult freezeRole(Long roleId) {
        return null;
    }

    /**
     * @param roleId 角色id
     * @return
     * @description 删除角色
     * @author WangBin
     * @date 2018/8/8 17:55
     */
    @PostMapping("removeRole")
    public JsonResult removeRole(Long roleId) {
        return null;
    }

    /**
     * @return
     * @description 获取地址列表
     * @author WangBin
     * @date 2018/8/8 14:35
     */
    @PostMapping("getAddress")
    public JsonResult getAddress() {
        return null;
    }

    /**
     * @description 新增地址
     * @author WangBin
     * @date 2018/8/9 17:27
     */
    @PostMapping("addAddress")
    public JsonResult addAddress(AddressDTO addressDTO) {
        return null;
    }

    /**
     * @description 修改地址
     * @author WangBin
     * @date 2018/8/9 17:34
     */
    @PostMapping("modifyAddress")
    public JsonResult modifyAddress(AddressDTO addressDTO) {
        return null;
    }

    /**
     * @param addressId 地址id
     * @return
     * @description 删除地址
     * @author WangBin
     * @date 2018/8/8 17:52
     */
    @PostMapping("removeAddress")
    public JsonResult removeAddress(Long addressId) {
        return null;
    }

    /**
     * @param addressId 地址id
     * @return
     * @description 设置默认地址
     * @author WangBin
     * @date 2018/8/8 14:36
     */
    @PostMapping("setDefaultAddress")
    public JsonResult setDefaultAddress(Long addressId) {
        return null;
    }

    /**
     * @return
     * @description 获取小B列表
     * @author WangBin
     * @date 2018/8/8 18:04
     */
    @PostMapping("getRentMerchantList")
    public JsonResult getRentMerchantList() {
        return null;
    }

    /**
     * @param businessListen              营业执照文件 2张
     * @param highestDegreeDiploma        最高学历毕业证文件 2张
     * @param drivingLicense              驾驶证文件 2张
     * @param houseProprietaryCertificate 房产证文件 9张
     * @return
     * @description 大B添加小B
     * @author WangBin
     * @date 2018/8/9 17:46
     */
    @PostMapping("addRentMerchant")
    public JsonResult addRentMerchant(RoleApplyDTO roleApplyDTO, MultipartFile[] businessListen,
                                      MultipartFile[] highestDegreeDiploma, MultipartFile[] drivingLicense,
                                      MultipartFile[] houseProprietaryCertificate) {
        return null;
    }

    /**
     * @description 修改小B的信息
     * @author WangBin
     * @date 2018/8/9 17:49
     */
    @PostMapping("modifyRentMerchant")
    public JsonResult modifyRentMerchant(RentMerchantModifyDTO rentMerchantModifyDTO) {
        return null;
    }

    /**
     * @param removeUid 要删除的小B uid
     * @return
     * @description 删除小B
     * @author WangBin
     * @date 2018/8/8 18:00
     */
    @PostMapping("removeRentmerchant")
    public JsonResult removeRentmerchant(Long removeUid) {
        return null;
    }

    /**
     * @param files 文件
     * @return
     * @description 上传文件
     * @author WangBin
     * @date 2018/8/15 10:46
     */
    @PostMapping("uploadFile")
    public JsonResult uploadFile(MultipartFile[] files) {
        return null;
    }
}
