package com.newframe.utils;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * restful工具类
 * <p>
 * Created by tt on 7/13/18.
 */
public class RestUtils {
    private static RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(4000);//4秒
        httpRequestFactory.setReadTimeout(16000);
        restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
