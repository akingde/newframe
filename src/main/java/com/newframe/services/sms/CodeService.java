package com.newframe.services.sms;

import com.newframe.dto.OperationResult;
import com.newframe.dto.mq.AliVcode;
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

    /**
     * 手机验证码校验, 只能校验一次，校验后就删除验证码
     * @param mobile
     * @param mcodeType
     * @param mcode
     * @return
     */
    Boolean isValidMcode(String mobile, McodeTypeEnum mcodeType, String mcode);

    /**
     * 判断短信验证码是否存在, 不会删除验证码
     * @param mobile
     * @param mcodeType
     * @param mcode
     * @return
     */
    Boolean hasMcode(String mobile, McodeTypeEnum mcodeType, String mcode);

}
