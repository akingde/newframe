package com.newframe.services.account;

import com.newframe.dto.OperationResult;
import com.newframe.dto.account.RenterAccountInfo;

/**
 * @author:wangdong
 * @description:
 */
public interface AccountManageService {

    /**
     * 租赁商获取账户信息
     * @param uid
     * @return
     */
    OperationResult<RenterAccountInfo> getRenterAccountInfo(Long uid);
}
