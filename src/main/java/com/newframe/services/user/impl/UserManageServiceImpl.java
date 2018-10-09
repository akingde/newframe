package com.newframe.services.user.impl;

import com.newframe.dto.OperationResult;
import com.newframe.enums.BizErrorCode;
import com.newframe.enums.sms.AliyunSMSTemplateEnum;
import com.newframe.enums.sms.McodeTypeEnum;
import com.newframe.services.sms.CodeService;
import com.newframe.services.user.UserManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:wangdong
 * @description:用户管理类的Service
 */
@Service
public class UserManageServiceImpl implements UserManageService {

    @Autowired
    private CodeService codeService;

    /**
     * 根据手机号获取验证码
     *
     * @param mobile
     * @param mcodeType
     * @return
     */
    @Override
    public OperationResult<String> getMcode(String mobile, Integer mcodeType) {
        if (StringUtils.isEmpty(mobile) || null == mcodeType) {
            return new OperationResult<>(BizErrorCode.PARM_ERROR);
        }
        OperationResult<String> result = new OperationResult<>();
        if (McodeTypeEnum.REGISTER_OR_LOGIN.getCode().equals(mcodeType)){
            result = codeService.sendMcode(mobile, McodeTypeEnum.getEnum(mcodeType), AliyunSMSTemplateEnum.REGISTER_OR_LOGIN);
        }
        if (result.getSucc()){
            return new OperationResult<>(result.getEntity());
        }

        return new OperationResult<>(result.getErrorCode());
    }
}
