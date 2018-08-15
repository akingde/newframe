package com.newframe.services.user.userimpl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.response.UserBaseInfoDTO;
import com.newframe.services.user.RoleService;
import com.newframe.services.user.UserService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author WangBin
 */
@Service
public class UserServiceImpl implements UserService {

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
        return null;
    }

    @Override
    public OperationResult<Boolean> register(String mobile, String mCode) {
        return null;
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
    @Override
    public OperationResult<UserBaseInfoDTO> verificationCodeLogin() {
        return null;
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
        return null;
    }

    /**
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
    public OperationResult<Boolean> setPassword(String password, String confirmPassword) {
        return null;
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
    public OperationResult<Boolean> modifyPassword(String oldPassword, String newPassword, String confirmPassword) {
        return null;
    }

    /**
     * @param mobile
     * @param code
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
    public OperationResult<Boolean> forgetPassword(String mobile, Integer code, String password) {
        return null;
    }

    /**
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
    public OperationResult<Boolean> modifyMobile(String newMobile, String mobileCode) {
        return null;
    }

    /**
     * @param mobileCode
     * @Description 注销手机号
     * @Author WangBin
     * @Param mobileCode 验证码
     * @Return
     * @Date 2018/8/9 16:19
     */
    @Override
    public OperationResult<Boolean> removeMobile(String mobileCode) {
        return null;
    }
}
