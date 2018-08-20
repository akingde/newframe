package com.newframe.services.common;

import com.newframe.dto.OperationResult;
import com.newframe.dto.common.ExpressInfo;
import org.springframework.stereotype.Service;

/**
 * @author:wangdong
 * @description:
 */
public interface CommonService {

    /**
     * 根据快递公司和快递单号
     * 查询快递信息
     * @param expCode
     * @param expNo
     * @return
     */
    OperationResult<ExpressInfo> getExpressMessage(String expCode, String expNo);
}
