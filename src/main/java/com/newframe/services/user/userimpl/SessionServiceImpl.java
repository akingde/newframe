package com.newframe.services.user.userimpl;

import com.newframe.blockchain.util.KeyUtil;
import com.newframe.dto.LoginInfo;
import com.newframe.dto.OperationResult;
import com.newframe.entity.user.UserAppToken;
import com.newframe.enums.SystemCode;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.services.user.SessionService;
import com.newframe.services.userbase.UserAppTokenService;
import com.newframe.services.userbase.UserWebTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author WangBin
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private UserAppTokenService userAppTokenService;
    @Autowired
    private UserWebTokenService userWebTokenService;
    @Autowired
    private RedisTemplate redisTemplate;

    private final static String app_prefix = "mobile_app_";
    private final static String web_prefix = "mobile_web_";

    private final static String prefix= "mobile_";

    /**
     * 设置app token
     *
     * @param uid
     * @return
     */
    @Override
    public String setAppUserToken(Long uid) {
        String token = UUID.randomUUID().toString();
        userAppTokenService.insert(uid, token);
        setCacheToken(app_prefix + uid, token);
        return token;
    }

    /**
     * 设置web token
     *
     * @param uid
     * @return
     */
    @Override
    public String setWebUserToken(Long uid) {
        String token = UUID.randomUUID().toString();
        userWebTokenService.insert(uid, token);
        setCacheToken(web_prefix + uid, token);
        return token;
    }

    /**
     * 更新app用户token
     *
     * @param uid
     * @param oldToken
     * @return
     */
    @Override
    public OperationResult<String> modifyAppUserToken(Long uid, String oldToken) {
        UserAppToken appToken = userAppTokenService.findOne(uid);
        if (appToken == null){
            return new OperationResult(RequestResultEnum.USER_NOT_EXISTS);
        }
        if(StringUtils.isAnyEmpty(oldToken, appToken.getToken()) || !oldToken.equals(appToken.getToken())){
            return new OperationResult(SystemCode.NEED_LOGIN);
        }
        String token = UUID.randomUUID().toString();
        userAppTokenService.updateByUid(uid, token);
        setCacheToken(app_prefix + uid, token);
        return new OperationResult(token);
    }

    /**
     * 更新app用户token
     *
     * @param uid
     * @return
     */
    @Override
    public String modifyAppUserToken(Long uid) {
        String token = UUID.randomUUID().toString();
        userWebTokenService.updateByUid(uid, token);
        setCacheToken(app_prefix + uid, token);
        return token;
    }

    /**
     * 更新webToken
     *
     * @param uid
     * @return
     */
    @Override
    public String modifyWebUserToken(Long uid) {
        String token = UUID.randomUUID().toString();
        userAppTokenService.updateByUid(uid, token);
        setCacheToken(web_prefix + uid, token);
        return token;
    }

    /**
     * 清空app用户token
     *
     * @param uid
     * @return
     */
    @Override
    public void cleanAppUserToken(Long uid) {
        userWebTokenService.updateByUid(uid, null);
        redisTemplate.delete(app_prefix + uid);
    }

    /**
     * 清空web用户token
     *
     * @param uid
     * @return
     */
    @Override
    public void cleanWebUserToken(Long uid) {
        userAppTokenService.updateByUid(uid, null);
        redisTemplate.delete(web_prefix + uid);
    }

    /**
     * 校验用户token和uid
     *
     * @param token
     * @param uid
     * @param isWeb
     * @return
     */
    @Override
    public LoginInfo validateLoginInfo(String token, String uid, boolean isWeb) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(uid)) {
            return null;
        }
        String key = isWeb ? web_prefix + uid : app_prefix + uid;
        Object redisToken = redisTemplate.opsForValue().get(key);
        if (null == redisToken) {
            redisToken = redisTemplate.opsForValue().get(key);
        }
        if (null == redisToken) {
            return null;
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUid(Long.valueOf(uid));
        loginInfo.setToken(token);
        return loginInfo;

    }

    public void setCacheToken(String key, String token){
        redisTemplate.delete(key);
        redisTemplate.opsForValue().set(key, token);
        redisTemplate.expire(key, 2, TimeUnit.HOURS);
    }

    /**
     * 保存验证码
     *
     * @param mobile
     * @param type
     * @param code
     * @return
     */
    @Override
    public void saveCode(String mobile, Integer type, String code) {
        String key = prefix + mobile + type;
        redisTemplate.delete(key);
        redisTemplate.opsForValue().set(key, code);
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);
    }

    /**
     * 校验验证码
     *
     * @param mobile
     * @param type
     * @param code
     * @return
     */
    @Override
    public boolean checkCode(String mobile, Integer type, String code) {
//        String key = prefix + mobile + type;
//        Object redisCode = redisTemplate.opsForValue().get(key);
//        if(redisCode == null){
//            return false;
//        }
//        redisTemplate.delete(key);
//        return code.equals(redisCode);
        return true;
    }
}
