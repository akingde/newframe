package com.newframe.services.user;

import com.newframe.dto.LoginInfo;
import com.newframe.dto.OperationResult;

/**
 * @author WangBin
 */
public interface SessionService {

    /**
     * 设置app token
     * @param uid
     * @return
     */
    String setAppUserToken(Long uid);

    /**
     * 设置web token
     * @param uid
     * @return
     */
    String setWebUserToken(Long uid);

    /**
     * 更新app用户token
     * @param uid
     * @param oldToken
     * @return
     */
    OperationResult<String> modifyAppUserToken(Long uid, String oldToken);

    /**
     * 更新app用户token
     * @param uid
     * @return
     */
    String modifyAppUserToken(Long uid);

    /**
     * 更新webToken
     * @param uid
     * @return
     */
    String modifyWebUserToken(Long uid);

    /**
     * 清空app用户token
     * @param uid
     * @return
     */
    void cleanAppUserToken(Long uid);

    /**
     * 清空web用户token
     * @param uid
     * @return
     */
    void cleanWebUserToken(Long uid);

    /**
     * 校验用户token和uid
     * @param token
     * @param uid
     * @param isWeb
     * @return
     */
    LoginInfo validateLoginInfo(String token, String uid, boolean isWeb);
}
