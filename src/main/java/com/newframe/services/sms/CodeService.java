package com.newframe.services.sms;

import com.newframe.dto.OperationResult;
import com.newframe.enums.sms.AliyunSMSTemplateEnum;
import com.newframe.enums.sms.McodeTypeEnum;

/**
 * @author:wangdong
 * @description:短信相关的Service
 */
public interface CodeService {


    /**
     *
     * 发送短信验证码
     * @param mobile
     * @param mCodeType
     * @param aliyunSMSTemplateEnum
     * @return
     */
    OperationResult<String> sendMcode(String mobile, McodeTypeEnum mCodeType, AliyunSMSTemplateEnum aliyunSMSTemplateEnum);

}
