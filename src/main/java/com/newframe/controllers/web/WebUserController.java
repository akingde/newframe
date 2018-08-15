package com.newframe.controllers.web;

import com.newframe.common.anony.Anonymous;
import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("getRoleApplyList")
    public JsonResult getRoleApplyList(Integer roleId) {
        return null;
    }

    @PostMapping("")
    public JsonResult modifyRoleApplyInfo(){
        return null;
    }

    /**
     * @param name                        商家名称
     * @param legalEntity                 法人姓名
     * @param legalEntityIdNumber         法人身份证号
     * @param businessListenNumber        营业执照编号
     * @param businessListen              营业执照文件 2张
     * @param highestDegreeDiploma        最高学历毕业证文件 2张
     * @param drivingLicense              驾驶证文件 2张
     * @param houseProprietaryCertificate 房产证文件 9张
     * @return
     * @description 租赁商角色申请
     * @author WangBin
     * @date 2018/8/9 16:43
     */
    @PostMapping("rentMerchantRoleApply")
    public JsonResult rentMerchantRoleApply(String name, String legalEntity, String legalEntityIdNumber,
                                            String businessListenNumber, MultipartFile[] businessListen,
                                            MultipartFile[] highestDegreeDiploma, MultipartFile[] drivingLicense,
                                            MultipartFile[] houseProprietaryCertificate) {
        return null;
    }

    /**
     * @param roleAppleId 角色申请id
     * @return
     * @description 获取租赁商申请详细信息
     * @author WangBin
     * @date 2018/8/13 14:34
     */
    @PostMapping("getRentMechantApplyInfo")
    public JsonResult getRentMechantApplyInfo(Long roleAppleId) {
        return null;
    }

    /**
     * @param roleAppleId
     * @param legalEntity
     * @param legalEntityIdNumber
     * @param businessListenNumber
     * @param businessListen
     * @param highestDegreeDiploma
     * @param drivingLicense
     * @param houseProprietaryCertificate
     * @return
     * @description 租赁商信息修改
     * @author WangBin
     * @date 2018/8/15 11:14
     */
    @PostMapping("modifyRentMerchantApplyInfo")
    public JsonResult modifyRentMerchantApplyInfo(Long roleAppleId, String legalEntity, String legalEntityIdNumber,
                                                  String businessListenNumber, String[] businessListen,
                                                  String[] highestDegreeDiploma, String[] drivingLicense,
                                                  String[] houseProprietaryCertificate) {
        return null;
    }

    /**
     * @param name                   商家名称
     * @param legalEntity            法人姓名
     * @param legalEntityIdNumber    法人身份证号
     * @param topContacts            紧急联系人姓名
     * @param topContactsPhoneNumber 联系电话
     * @param relationship           关系
     * @param businessListenNumber   营业执照编号
     * @param businessListen         营业执照文件 2张
     * @param letterOfAttorney       授权委托书 2张
     * @param businessQualification  经营资质 2张
     * @return
     * @description 资金方角色申请
     * @author WangBin
     * @date 2018/8/9 16:50
     */
    @PostMapping("funderRoleApply")
    public JsonResult funderRoleApply(String name, String legalEntity, String legalEntityIdNumber,
                                      String topContacts, String topContactsPhoneNumber, Integer relationship,
                                      String businessListenNumber, MultipartFile[] businessListen,
                                      MultipartFile[] letterOfAttorney, MultipartFile[] businessQualification) {
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
     * @param roleApplyId           角色申请id
     * @param name                   商家名称
     * @param legalEntity            法人姓名
     * @param legalEntityIdNumber    法人身份证号
     * @param topContacts            紧急联系人姓名
     * @param topContactsPhoneNumber 联系电话
     * @param relationship           关系
     * @param businessListenNumber   营业执照编号
     * @param businessListen         营业执照文件 2张
     * @param letterOfAttorney       授权委托书 2张
     * @param businessQualification  经营资质 2张
     * @return
     * @description 资金方角色申请信息修改
     * @author WangBin
     * @date 2018/8/9 16:50
     */
    @PostMapping("modifyFunderApplyInfo")
    public JsonResult modifyFunderApplyInfo(Long roleApplyId, String name, String legalEntity, String legalEntityIdNumber,
                                            String topContacts, String topContactsPhoneNumber, Integer relationship,
                                            String businessListenNumber, String[] businessListen,
                                            String[] letterOfAttorney, String[] businessQualification){
        return null;
    }

    /**
     * @param name                   商家名称
     * @param legalEntity            法人姓名
     * @param legalEntityIdNumber    法人身份证号
     * @param topContacts            紧急联系人姓名
     * @param topContactsPhoneNumber 联系电话
     * @param relationship           关系
     * @param businessListenNumber   营业执照编号
     * @param businessListen         营业执照文件 2张
     * @return
     * @description 出租方申请
     * @author WangBin
     * @date 2018/8/9 16:52
     */
    @PostMapping("hirerRoleApply")
    public JsonResult hirerRoleApply(String name, String legalEntity, String legalEntityIdNumber,
                                     String topContacts, String topContactsPhoneNumber, Integer relationship,
                                     String businessListenNumber, MultipartFile[] businessListen) {
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
     * @param name                   商家名称
     * @param legalEntity            法人姓名
     * @param legalEntityIdNumber    法人身份证号
     * @param topContacts            紧急联系人姓名
     * @param topContactsPhoneNumber 联系电话
     * @param relationship           关系
     * @param businessListenNumber   营业执照编号
     * @param businessListen         营业执照文件 2张
     * @return
     * @description 修改出租方信息
     * @author WangBin
     * @date 2018/8/9 16:52
     */
    @PostMapping("modifyHirerApplyInfo")
    public JsonResult modifyHirerApplyInfo(Long roleApplyId, String name, String legalEntity, String legalEntityIdNumber,
                                           String topContacts, String topContactsPhoneNumber, Integer relationship,
                                           String businessListenNumber, String[] businessListen){
        return null;
    }

    /**
     * @param name                 商家名称
     * @param legalEntity          法人姓名
     * @param legalEntityIdNumber  法人身份证号
     * @param legalEntityIdNumber  法人身份证号
     * @param businessListenNumber 营业执照编号
     * @param businessListen       营业执照文件 2张
     * @return
     * @description 供应商角色申请
     * @author WangBin
     * @date 2018/8/9 16:59
     */
    @PostMapping("supplierRoleApply")
    public JsonResult supplierRoleApply(String name, String legalEntity, String legalEntityIdNumber,
                                        String businessListenNumber, MultipartFile[] businessListen) {
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
     * @param roleApplyId        角色申请id
     * @param name                 商家名称
     * @param legalEntity          法人姓名
     * @param legalEntityIdNumber  法人身份证号
     * @param legalEntityIdNumber  法人身份证号
     * @param businessListenNumber 营业执照编号
     * @param businessListen       营业执照文件 2张
     * @return
     * @description 供应商角色信息修改
     * @author WangBin
     * @date 2018/8/9 16:59
     */
    @PostMapping("modifySupplierApplyInfo")
    public JsonResult modifySupplierApplyInfo(Long roleApplyId, String name, String legalEntity,
                                                  String legalEntityIdNumber,  String businessListenNumber,
                                                  String[] businessListen) {
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
     * @param defaultAddress   默认地址
     * @param consigneeName    接收人姓名
     * @param provinceId       省id
     * @param cityId           市id
     * @param countyId         县/地级市/区id
     * @param consigneeAddress 收件详细地址
     * @param mobile           手机号
     * @return
     * @description 新增地址
     * @author WangBin
     * @date 2018/8/9 17:27
     */
    @PostMapping("addAddress")
    public JsonResult addAddress(boolean defaultAddress, String consigneeName, Integer provinceId, Integer cityId,
                                 Integer countyId, String consigneeAddress, String mobile) {
        return null;
    }

    /**
     * @param addressId        地址id
     * @param defaultAddress   默认地址
     * @param consigneeName    接收人姓名
     * @param provinceId       省id
     * @param cityId           市id
     * @param countyId         县/地级市/区id
     * @param consigneeAddress 收件详细地址
     * @param mobile           手机号
     * @return
     * @description 修改地址
     * @author WangBin
     * @date 2018/8/9 17:34
     */
    @PostMapping("modifyAddress")
    public JsonResult modifyAddress(Long addressId, boolean defaultAddress, String consigneeName, Integer provinceId,
                                    Integer cityId, Integer countyId, String consigneeAddress, String mobile) {
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
     * @param merchantName                商家名称
     * @param legalEntity                 法人姓名
     * @param legalEntityIdNumber         法人身份证号
     * @param phone                       手机号
     * @param provinceId                  省id
     * @param cityId                      市id
     * @param countyId                    县/地级市/区id
     * @param consigneeAddress            收件详细地址
     * @param businessListenNumber        营业执照编号
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
    public JsonResult addRentMerchant(String merchantName, String legalEntity, String legalEntityIdNumber, String phone,
                                      Integer provinceId, Integer cityId, Integer countyId, String consigneeAddress,
                                      String businessListenNumber, String businessListen,
                                      String highestDegreeDiploma, String drivingLicense,
                                      String houseProprietaryCertificate) {
        return null;
    }

    /**
     * @param modifyUid                   修改的租赁商uid
     * @param name                        商家名称
     * @param legalEntity                 法人姓名
     * @param legalEntityIdNumber         法人身份证号
     * @param provinceId                  省id
     * @param cityId                      市id
     * @param countyId                    县/地级市/区id
     * @param consigneeAddress            收件详细地址
     * @param businessListenNumber        营业执照编号
     * @param businessListen              营业执照文件 2张
     * @param highestDegreeDiploma        最高学历毕业证文件 2张
     * @param drivingLicense              驾驶证文件 2张
     * @param houseProprietaryCertificate 房产证文件 9张
     * @return
     * @description 修改小B的信息
     * @author WangBin
     * @date 2018/8/9 17:49
     */
    @PostMapping("modifyRentMerchant")
    public JsonResult modifyRentMerchant(Long modifyUid, String name, String legalEntity, String legalEntityIdNumber,
                                         Integer provinceId, Integer cityId, Integer countyId, String consigneeAddress,
                                         String businessListenNumber, String[] businessListen,
                                         String[] highestDegreeDiploma, String[] drivingLicense,
                                         String[] houseProprietaryCertificate) {
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
