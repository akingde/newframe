package com.newframe.services.sms.impl;

import com.newframe.dto.SmsResult;
import com.newframe.resp.sms.SendCodeResp;
import com.newframe.services.http.OkHttpService;
import com.newframe.services.sms.SmsService;
import com.newframe.utils.log.GwsLogger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:wangdong
 * @description:短信服务实现类
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private OkHttpService okHttpService;


    /***
     * 发送阿里云短信验证码
     * @param mobile
     * @param templateCode
     * @param code
     * @return
     */
    @Override
    public Boolean sendAliVcode(String mobile, String templateCode, String code) {
        if (StringUtils.isEmpty(mobile) || null == templateCode || StringUtils.isEmpty(code)) {
            return false;
        }

        Map<String,Object> params = new HashMap<>();
        params.put("mobile",mobile);
        params.put("templateCode", templateCode);
        params.put("code", code);
        GwsLogger.info("给{}发送验证码{}", mobile, code);
        SmsResult smsResult = okHttpService.sendVerificationCode(mobile, templateCode, code);
        if (null != smsResult && smsResult.getData().getStatus()){
            return true;
        }

        return false;
    }
}
