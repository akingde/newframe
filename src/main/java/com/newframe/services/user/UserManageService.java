package com.newframe.services.user;

import com.newframe.dto.LoginInfo;
import com.newframe.dto.OperationResult;
import com.newframe.dto.mq.AliVcode;
import com.newframe.dto.user.RegisterInfo;
import com.newframe.enums.sms.McodeTypeEnum;

/**
 * @author:wangdong
 * @description:用户管理类的Service
 */
public interface UserManageService {

    /**
     * 根据手机号获取验证码
     * @param mobile
     * @param mcodeType
     * @return
     */
    OperationResult<String> getMcode(String mobile, Integer mcodeType);

    /**
     * 通过消息队列发送验证码
     * @param aliVcode
     * @param mcodeTypeEnum
     * @return
     */
    OperationResult<String> sendMcode(AliVcode aliVcode, McodeTypeEnum mcodeTypeEnum);
    /**
     * 用户的注册或者登陆
     * @param mobile
     * @param mcode
     * @param roleType
     * @return
     */
    OperationResult<LoginInfo> mobileRegisterOrLogin(String mobile, String mcode, Integer roleType);

    /**
     * 根据手机号判断用户是否设置过密码
     * @param mobile
     * @return
     */
    OperationResult<RegisterInfo> mobileHasRegister(String mobile);

    /**
     * 通过手机号码设置密码
     * @param mobile
     * @param mcode
     * @param pwd
     * @param passWordType
     * @return
     */
    OperationResult<Boolean> setPasswordByMobile(String mobile, String mcode, String pwd, Integer passWordType);
}
