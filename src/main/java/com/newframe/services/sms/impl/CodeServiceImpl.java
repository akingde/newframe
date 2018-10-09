package com.newframe.services.sms.impl;

import com.newframe.common.cache.CachePrefix;
import com.newframe.dto.OperationResult;
import com.newframe.enums.BizErrorCode;
import com.newframe.enums.sms.AliyunSMSTemplateEnum;
import com.newframe.enums.sms.McodeTypeEnum;
import com.newframe.services.sms.CodeService;
import com.newframe.services.sms.SmsService;
import com.newframe.utils.log.GwsLogger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author:wangdong
 * @description:短信相关的Service
 */
@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsService smsService;

    @Value("${mcode.open}")
    private boolean mcodeOpen;

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @param mCodeType
     * @param aliyunSMSTemplateEnum
     * @return
     */
    @Override
    public OperationResult<String> sendMcode(String mobile, McodeTypeEnum mCodeType, AliyunSMSTemplateEnum aliyunSMSTemplateEnum) {
        /**1分钟限制调用1次**/
        String key = CachePrefix.MCODE_LOCK + mobile;
        if (redisTemplate.opsForValue().setIfAbsent(key, true)) {
            redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        } else {
            return new OperationResult<>(BizErrorCode.MCODE_ONE_MINIT_LIMIT);
        }


        String code = randomVcode(6);

        /**处理返回结果**/
        if (smsService.sendAliVcode(mobile, aliyunSMSTemplateEnum.getTemplateCode(), code)) {
            StringBuilder mcodeKey = new StringBuilder(CachePrefix.MCODE).
                    append(mCodeType.getCode()).append("_").append(mobile);

            redisTemplate.opsForValue().set(mcodeKey.toString(), code, 10, TimeUnit.MINUTES);
            GwsLogger.info("给{}发送{}验证码【{}】", mobile, mCodeType.getMessage(), code);

            return new OperationResult<>(code);
        } else {
            return new OperationResult<>(BizErrorCode.MCODE_SEND_ERROR);
        }
    }

    /**
     * 【获取指定位数的数字验证码】
     * @param bit 位数
     * @return
     */
    private String randomVcode(int bit) {
        StringBuilder vcode = new StringBuilder();
        for(int i=0; i<bit; i++) {
            vcode.append(new Random().nextInt(9));
        }
        return vcode.toString();
    }

    /**
     *
     * 校验验证码, 只能校验一次，校验后就删除验证码
     * @param mobile
     * @param mcodeType
     * @param mcode
     * @return
     */
    @Override
    public Boolean isValidMcode(String mobile, McodeTypeEnum mcodeType, String mcode) {
        if (StringUtils.isEmpty(mobile) || null == mcodeType || StringUtils.isEmpty(mcode)){
            return false;
        }
        if(!mcodeOpen){
            return true;
        }

        boolean isValid = hasMcode(mobile, mcodeType, mcode);
        if (!isValid) {
            return false;
        }

        String redisKey = new StringBuilder(CachePrefix.MCODE).
                append(mcodeType.getCode()). append("_").append(mobile).toString();

        redisTemplate.delete(redisKey);
        return true;
    }

    /**
     *
     * 判断短信验证码是否存在, 不会删除验证码
     * @param mobile
     * @param mcodeType
     * @param mcode
     * @return
     */
    @Override
    public Boolean hasMcode(String mobile, McodeTypeEnum mcodeType, String mcode) {

        if (StringUtils.isEmpty(mobile) || null == mcodeType || StringUtils.isEmpty(mcode)) {
            return false;
        }

        String redisKey = new StringBuilder(CachePrefix.MCODE).
                append(mcodeType.getCode()). append("_").append(mobile).toString();
        String value = (String) redisTemplate.opsForValue().get(redisKey);


        if (mcode.equals(value)) {
            return true;
        } else {
            //todo
            //提测后改回来
            return false;
        }
    }

}
