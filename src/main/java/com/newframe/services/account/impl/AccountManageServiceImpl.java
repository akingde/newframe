package com.newframe.services.account.impl;

import com.google.common.collect.Lists;
import com.newframe.dto.OperationResult;
import com.newframe.dto.account.RenterAccountInfo;
import com.newframe.dto.account.RenterAddresses;
import com.newframe.dto.account.RenterAuthorization;
import com.newframe.dto.account.RenterBaseInfo;
import com.newframe.enums.BizErrorCode;
import com.newframe.services.account.AccountManageService;
import com.newframe.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
@Service
public class AccountManageServiceImpl implements AccountManageService {

    @Autowired
    private AccountService accountService;
    /**
     * 租赁商获取账户信息
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<RenterAccountInfo> getRenterAccountInfo(Long uid) {
        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }

        RenterAccountInfo renterAccountInfo = new RenterAccountInfo();

        RenterBaseInfo renterBaseInfo = new RenterBaseInfo();
        List<RenterAddresses> renterAddresses = Lists.newArrayList();
        RenterAuthorization renterAuthorization = new RenterAuthorization();

        //accountService.getRenterBaseInfo(uid);

        //accountService.listRenterAddresses();

         //accountService.getRenterAuthorization();

        return new OperationResult<>(renterAccountInfo);
    }
}
