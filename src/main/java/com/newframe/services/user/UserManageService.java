package com.newframe.services.user;

import com.newframe.dto.OperationResult;

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
}
