package com.newframe.services.sms;

/**
 * @author:wangdong
 * @description:发短信的service
 */
public interface SmsService {

    /***
     * 发送阿里云短信验证码
     * @param mobile
     * @param templateCode
     * @param code
     * @return
     */
    Boolean sendAliVcode(String mobile, String templateCode, String code);
}
