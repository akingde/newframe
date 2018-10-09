package com.newframe.resp.sms;

import com.aliyuncs.CommonResponse;
import lombok.Data;

/**
 * Created by wd on 17-7-15.
 */
@Data
public class SendCodeResp extends CommonResponse {

    private String data;
}
