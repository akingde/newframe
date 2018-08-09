package com.newframe.controllers.web;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:wangdong
 * @description:用户相关模块的Controller
 */
@RestController
@RequestMapping("/app/user/")
public class AppUserController extends BaseController {

    /**
     * @Description 注册
     * @Author WangBin
     * @Param mobile 手机号
     * @Param mCode 验证码
     * @Return
     * @Date 2018/8/8 14:31
     */
    @PostMapping("register")
    public JsonResult register(String mobile, String mCode) {
        return null;
    }

    /**
     * @Description 校验手机号是否存在
     * @Author WangBin
     * @Param mobile 手机号
     * @Return
     * @Date 2018/8/8 14:31
     */
    @PostMapping("checkMobileExists")
    public JsonResult checkMobileExists(String mobile) {

        return null;
    }

    /**
     * @Description 校验手机号是否有密码
     * @Author WangBin
     * @Param mobile 手机号
     * @Return
     * @Date 2018/8/8 14:32
     */
    @PostMapping("checkPasswordExists")
    public JsonResult checkPasswordExists(String mobile) {
        return null;
    }

    /**
     * @Description 密码登录
     * @Author WangBin
     * @Param mobile 手机号
     * @Param password 密码
     * @Return
     * @Date 2018/8/8 14:32
     */
    @PostMapping("passwordLogin")
    public JsonResult passwordLogin(String mobile, String password) {
        return null;
    }

    /**
     * @Description 发送验证码
     * @Author WangBin
     * @Param mobile 手机号
     * @Param codeType 验证码类型
     * @Return com.newframe.controllers.JsonResult
     * @Date 2018/8/9 15:48
     */
    @PostMapping("sendVerificationCode")
    public JsonResult sendVerificationCode(String mobile, Integer codeType) {
        return null;
    }

    /**
     * @Description 验证码登录
     * @Author WangBin
     * @Param mobile
     * @Param code
     * @Return
     * @Date 2018/8/9 16:05
     */
    @PostMapping("verificationCodeLogin")
    public JsonResult verificationCodeLogin(String mobile, String code) {
        return null;
    }

    /**
     * @Description 设置密码
     * @Author WangBin
     * @Param password 密码
     * @Param confirmPassword 确认密码
     * @Return
     * @Date 2018/8/9 16:08
     */
    @PostMapping("setPassword")
    public JsonResult setPassword(String password, String confirmPassword) {
        return null;
    }

    /**
     * @Description 修改密码
     * @Author WangBin
     * @Param oldPassword 原密码
     * @Param newPassword 新密码
     * @Param confirmPassword
     * @Return
     * @Date 2018/8/9 16:10
     */
    @PostMapping("modifyPassword")
    public JsonResult modifyPassword(String oldPassword, String newPassword, String confirmPassword) {
        return null;
    }

    /**
     * @Description 修改手机号
     * @Author WangBin
     * @Param newMobile 新手机号
     * @Param mobileCode 手机验证码
     * @Return
     * @Date 2018/8/9 16:16
     */
    @PostMapping("modifyMobile")
    public JsonResult modifyMobile(String newMobile, String mobileCode) {
        return null;
    }

    /**
     * @Description 冻结手机号
     * @Author WangBin
     * @Param mobileCode 验证码
     * @Return
     * @Date 2018/8/9 16:17
     */
    @PostMapping("freezeMobile")
    public JsonResult freezeMobile(String mobile, String mobileCode) {

        return null;
    }

    /**
     * @Description 注销手机号
     * @Author WangBin
     * @Param mobileCode 注销手机号
     * @Return
     * @Date 2018/8/9 16:19
     */
    @PostMapping("removeMobile")
    public JsonResult removeMobile(String mobileCode) {
        return null;
    }

    /**
     * @Description 获取角色申请列表
     * @Author WangBin
     * @Param roleId 角色id
     * @Return
     * @Date 2018/8/8 17:48
     */
    @PostMapping("getRoleApplyList")
    public JsonResult getRoleApplyList(Integer roleId) {
        return null;
    }

    /**
     * @Description 获取角色申请详细信息
     * @Author WangBin
     * @Param roleApplyId 角色申请id
     * @Return
     * @Date 2018/8/8 17:50
     */
    @PostMapping("getRoleApplyInfo")
    public JsonResult getRoleApplyInfo(Long roleApplyId) {
        return null;
    }

    /**
     * @Description 租赁商角色申请
     * @Author WangBin
     * @Param name 商家名称
     * @Param legalEntity 法人姓名
     * @Param legalEntityIdNumber 法人身份证号
     * @Param businessListenNumber 营业执照编号
     * @Param businessListen 营业执照文件 2张
     * @Param highestDegreeDiploma 最高学历毕业证文件 2张
     * @Param drivingLicense 驾驶证文件 2张
     * @Param houseProprietaryCertificate 房产证文件 9张
     * @Return
     * @Date 2018/8/9 16:43
     */
    @PostMapping("rentMerchantRoleApply")
    public JsonResult rentMerchantRoleApply(String name, String legalEntity, String legalEntityIdNumber,
                                            String businessListenNumber, MultipartFile[] businessListen,
                                            MultipartFile[] highestDegreeDiploma, MultipartFile[] drivingLicense,
                                            MultipartFile[] houseProprietaryCertificate) {
        return null;
    }

    /**
     * @Description 资金方角色申请
     * @Author WangBin
     * @Param name 商家名称
     * @Param legalEntity 法人姓名
     * @Param legalEntityIdNumber 法人身份证号
     * @Param topContacts 紧急联系人姓名
     * @Param topContactsPhoneNumber 联系电话
     * @Param relationship 关系
     * @Param businessListenNumber 营业执照编号
     * @Param businessListen 营业执照文件 2张
     * @Param letterOfAttorney 授权委托书 2张
     * @Param businessQualification 经营资质 2张
     * @Return
     * @Date 2018/8/9 16:50
     */
    @PostMapping("funderRoleApply")
    public JsonResult funderRoleApply(String name, String legalEntity, String legalEntityIdNumber,
                                      String topContacts, String topContactsPhoneNumber, Integer relationship,
                                      String businessListenNumber, MultipartFile[] businessListen,
                                      MultipartFile[] letterOfAttorney, MultipartFile[] businessQualification) {
        return null;
    }

    /**
     * @Description 出租方申请
     * @Author WangBin
     * @Param name 商家名称
     * @Param legalEntity 法人姓名
     * @Param legalEntityIdNumber 法人身份证号
     * @Param topContacts 紧急联系人姓名
     * @Param topContactsPhoneNumber 联系电话
     * @Param relationship 关系
     * @Param businessListenNumber 营业执照编号
     * @Param businessListen 营业执照文件 2张
     * @Return
     * @Date 2018/8/9 16:52
     */
    @PostMapping("hirerRoleApply")
    public JsonResult hirerRoleApply(String name, String legalEntity, String legalEntityIdNumber,
                                     String topContacts, String topContactsPhoneNumber, Integer relationship,
                                     String businessListenNumber, MultipartFile[] businessListen) {
        return null;
    }

    /**
     * @Description 供应商角色申请
     * @Author WangBin
     * @Param name 商家名称
     * @Param legalEntity 法人姓名
     * @Param legalEntityIdNumber 法人身份证号
     * @Param legalEntityIdNumber 法人身份证号
     * @Param businessListenNumber 营业执照编号
     * @Param businessListen 营业执照文件 2张
     * @Return
     * @Date 2018/8/9 16:59
     */
    @PostMapping("supplierRoleApply")
    public JsonResult supplierRoleApply(String name, String legalEntity, String legalEntityIdNumber,
                                        String businessListenNumber, MultipartFile[] businessListen) {
        return null;
    }

    /**
     * @Description 冻结角色
     * @Author WangBin
     * @Param roleId 角色id
     * @Return
     * @Date 2018/8/8 17:55
     */
    @PostMapping("freezeRole")
    public JsonResult freezeRole(Long roleId) {
        return null;
    }

    /**
     * @Description 删除角色
     * @Author WangBin
     * @Param roleId 角色id
     * @Return
     * @Date 2018/8/8 17:55
     */
    @PostMapping("removeRole")
    public JsonResult removeRole(Long roleId) {
        return null;
    }

    /**
     * @Description 获取地址列表
     * @Author WangBin
     * @Return
     * @Date 2018/8/8 14:35
     */
    @PostMapping("getAddress")
    public JsonResult getAddress() {
        return null;
    }

    /**
     * @Description 新增地址
     * @Author WangBin
     * @Param defaultAddress 默认地址
     * @Param consigneeName 接收人姓名
     * @Param provinceId 省id
     * @Param cityId 市id
     * @Param countyId 县/地级市/区id
     * @Param consigneeAddress 收件详细地址
     * @Param mobile 手机号
     * @Return
     * @Date 2018/8/9 17:27
     */
    @PostMapping("addAddress")
    public JsonResult addAddress(boolean defaultAddress, String consigneeName, Integer provinceId, Integer cityId,
                                 Integer countyId, String consigneeAddress, String mobile) {
        return null;
    }

    /**
     * @Description 修改地址
     * @Author WangBin
     * @Param addressId 地址id
     * @Param defaultAddress 默认地址
     * @Param consigneeName 接收人姓名
     * @Param provinceId 省id
     * @Param cityId 市id
     * @Param countyId 县/地级市/区id
     * @Param consigneeAddress 收件详细地址
     * @Param mobile 手机号
     * @Return
     * @Date 2018/8/9 17:34
     */
    @PostMapping("modifyAddress")
    public JsonResult modifyAddress(Long addressId, boolean defaultAddress, String consigneeName, Integer provinceId,
                                    Integer cityId, Integer countyId, String consigneeAddress, String mobile) {
        return null;
    }

    /**
     * @Description 删除地址
     * @Author WangBin
     * @Param addressId 地址id
     * @Return
     * @Date 2018/8/8 17:52
     */
    @PostMapping("removeAddress")
    public JsonResult removeAddress(Long addressId) {
        return null;
    }

    /**
     * @Description 设置默认地址
     * @Author WangBin
     * @Param addressId 地址id
     * @Return
     * @Date 2018/8/8 14:36
     */
    @PostMapping("setDefaultAddress")
    public JsonResult setDefaultAddress(Long addressId) {
        return null;
    }

    /**
     * @Description 获取小B列表
     * @Author WangBin
     * @Return
     * @Date 2018/8/8 18:04
     */
    @PostMapping("getRentMerchantList")
    public JsonResult getRentMerchantList() {
        return null;
    }

    /**
     * @Description 大B添加小B
     * @Author WangBin
     * @Param name 商家名称
     * @Param legalEntity 法人姓名
     * @Param legalEntityIdNumber 法人身份证号
     * @Param provinceId 省id
     * @Param cityId 市id
     * @Param countyId 县/地级市/区id
     * @Param consigneeAddress 收件详细地址
     * @Param businessListenNumber 营业执照编号
     * @Param businessListen 营业执照文件 2张
     * @Param highestDegreeDiploma 最高学历毕业证文件 2张
     * @Param drivingLicense 驾驶证文件 2张
     * @Param houseProprietaryCertificate 房产证文件 9张
     * @Return
     * @Date 2018/8/9 17:46
     */
    @PostMapping("addRentMerchant")
    public JsonResult addRentMerchant(String name, String legalEntity, String legalEntityNumber, Integer provinceId,
                                      Integer cityId, Integer countyId, String consigneeAddress,
                                      String businessListenNumber, MultipartFile[] businessListen,
                                      MultipartFile[] highestDegreeDiploma, MultipartFile[] drivingLicense,
                                      MultipartFile[] houseProprietaryCertificate) {
        return null;
    }

    /**
     * @Description
     * @Author WangBin
     * @Param modifyuid 修改的租赁商uid
     * @Param name 商家名称
     * @Param legalEntity 法人姓名
     * @Param legalEntityIdNumber 法人身份证号
     * @Param provinceId 省id
     * @Param cityId 市id
     * @Param countyId 县/地级市/区id
     * @Param consigneeAddress 收件详细地址
     * @Param businessListenNumber 营业执照编号
     * @Param businessListen 营业执照文件 2张
     * @Param highestDegreeDiploma 最高学历毕业证文件 2张
     * @Param drivingLicense 驾驶证文件 2张
     * @Param houseProprietaryCertificate 房产证文件 9张
     * @Return
     * @Date 2018/8/9 17:49
     */
    @PostMapping("modifyRentMerchant")
    public JsonResult modifyRentMerchant(Long modifyuId, String name, String legalEntity, String legalEntityNumber,
                                         Integer provinceId, Integer cityId, Integer countyId, String consigneeAddress,
                                         String businessListenNumber, MultipartFile[] businessListen,
                                         MultipartFile[] highestDegreeDiploma, MultipartFile[] drivingLicense,
                                         MultipartFile[] houseProprietaryCertificate) {
        return null;
    }

    /**
     * @Description 删除小B
     * @Author WangBin
     * @Param removeuId 要删除的小B uid
     * @Return
     * @Date 2018/8/8 18:00
     */
    @PostMapping("removeRentmerchant")
    public JsonResult removeRentmerchant(Long removeuId) {
        return null;
    }
}
