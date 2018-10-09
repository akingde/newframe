package com.newframe.dto.mq;

import lombok.Data;

/**
 * @author:wangdong
 * @description:发送验证码的操作
 */
@Data
public class AliVcode {

    private String mobile;

    private String templateCode;

    private String code;

    public AliVcode(String mobile,String templateCode,String code){
        this.mobile=mobile;
        this.templateCode=templateCode;
        this.code=code;
    }

}
