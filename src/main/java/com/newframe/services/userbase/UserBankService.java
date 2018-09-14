package com.newframe.services.userbase;

import com.newframe.entity.user.UserBank;

/**
 * @author WangBin
 */
public interface UserBankService {

    UserBank insert(UserBank userBank);

    int update(UserBank userBank);

    UserBank findOne(Long uid);
}
