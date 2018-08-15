package com.newframe.services.user;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.response.UserBaseInfoDTO;

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


    OperationResult<Boolean> register(String mobile, String mCode);

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
     * @Description 验证码登录
     * @Author WangBin
     * @Param mobile
     * @Param code
     * @Return
     * @Date 2018/8/9 16:05
     */
    OperationResult<UserBaseInfoDTO> verificationCodeLogin();

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
     * @Param password 密码
     * @Param confirmPassword 确认密码
     * @Return
     * @Date 2018/8/9 16:08
     */
    OperationResult<Boolean> setPassword(String password, String confirmPassword);

    /**
     * @Description 修改密码
     * @Author WangBin
     * @Param oldPassword 原密码
     * @Param newPassword 新密码
     * @Param confirmPassword
     * @Return
     * @Date 2018/8/9 16:10
     */
    OperationResult<Boolean> modifyPassword(String oldPassword, String newPassword, String confirmPassword);

    /**
     * @Description 忘记密码
     * @Author WangBin
     * @Param mobile 手机号
     * @Param code 验证码
     * @Param password 密码
     * @Return
     * @Date 2018/8/14 15:24
     */
    OperationResult<Boolean> forgetPassword(String mobile, Integer code, String password);

    /**
     * @Description 修改手机号
     * @Author WangBin
     * @Param newMobile 新手机号
     * @Param mobileCode 手机验证码
     * @Return
     * @Date 2018/8/9 16:16
     */
    OperationResult<Boolean> modifyMobile(String newMobile, String mobileCode);

    /**
     * @Description 注销手机号
     * @Author WangBin
     * @Param mobileCode 验证码
     * @Return
     * @Date 2018/8/9 16:19
     */
    OperationResult<Boolean> removeMobile(String mobileCode);
}
