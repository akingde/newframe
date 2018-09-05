package com.newframe.services.common;

import com.newframe.dto.OperationResult;
import com.newframe.dto.common.ExpressInfo;
import com.newframe.dto.message.UserMessageInfo;
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

    /**
     * 首页查询用户的消息列表
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    OperationResult<UserMessageInfo> listUserMessage(Long uid, Integer currentPage, Integer pageSize);
}
