package com.newframe.services.common;

import com.newframe.dto.OperationResult;
import com.newframe.dto.common.ExpressInfo;
import com.newframe.dto.message.UserMessageInfo;

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
     * @param roleId
     * @param pageSize
     * @param currentPage
     * @return
     */
    OperationResult<UserMessageInfo> listUserMessage(Long uid, Integer roleId, Integer pageSize, Integer currentPage);
}
