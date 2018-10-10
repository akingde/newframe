package com.newframe.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 【密码工具类】
 *
 * @author wangdong 2017/11/25
 */
public class PwdUtil {
    /**
     * 检查密码
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd) {
        if (StringUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 18) {
            return false;
        }
        return true;
    }

    /**
     * 对密码进行MD5加密
     * @param pwd
     * @return
     */
    public static String md5Pwd(String pwd) {
        return MD5Util.MD5(MD5Util.MD5(pwd));
    }
}
