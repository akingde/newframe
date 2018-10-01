package com.newframe.services.account.impl;

import com.newframe.repositories.dataMaster.account.AccountFundingMaster;
import com.newframe.repositories.dataMaster.account.AccountLessorMaster;
import com.newframe.repositories.dataMaster.account.AccountSupplierMaster;
import com.newframe.services.account.AccountStatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户统计
 */
@Service
public class AccountStatisServiceImpl implements AccountStatisService {
    @Autowired
    AccountFundingMaster accountFundingMaster;
    @Autowired
    AccountLessorMaster accountLessorMaster;
    @Autowired
    AccountSupplierMaster accountSupplierMaster;

    @Override
    public void statisAccountRenter(long uid) {
    }

    @Override
    public void statisAccountFunding(long uid) {
        accountFundingMaster.statisAccount(uid);
    }

    @Override
    public void statisAccountLessor(long uid) {
        accountLessorMaster.statisAccount(uid);
    }

    @Override
    public void statisAccountSupplier(long uid) {
        accountSupplierMaster.statisAccount(uid);
    }
}
