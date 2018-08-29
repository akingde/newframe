
package com.newframe.interceptor;

import com.newframe.common.anony.Anonymous;
import com.newframe.common.anony.UserType;
import com.newframe.dto.LoginInfo;
import com.newframe.dto.RequestUser;
import com.newframe.enums.TypeEnum;
import com.newframe.services.user.SessionService;
import com.newframe.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;


/**
 * 【身份鉴定拦截器，通过token解析用户身份】
 *
 * @author wangdong
 * @version 4.0.0
 */
public class IdentityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = cookieUtil.getValue(request, CookieUtil.TOKEN);
        String uid = cookieUtil.getValue(request,CookieUtil.UID);

        if (StringUtils.isNotEmpty(token) || StringUtils.isNotEmpty(uid)) {
            UserType userType = handler.getClass().getAnnotation(UserType.class);
            boolean isWeb = userType.type().equals(TypeEnum.web);
            LoginInfo loginInfo = sessionService.validateLoginInfo(token, uid, isWeb);
            if (null != loginInfo) {
                RequestUser.User user = new RequestUser.User();
                user.setToken(loginInfo.getToken());
                user.setUid(loginInfo.getUid());
                RequestUser.put(user);
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        RequestUser.clear();
        super.postHandle(request, response, handler, modelAndView);
    }
}
