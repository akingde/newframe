package com.newframe.repositories.dataMaster.account;


import com.newframe.entity.account.AccountFunding;
import com.newframe.utils.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


/**
 * <p>
 * 账户操作的表
 * </p>
 *
 * @since 2018-08-29
 */
public interface AccountFundingMaster extends BaseRepository<AccountFunding, Long> {

    @Transactional
    @Modifying
    @Query(value = "call pro_account_funding(?1)", nativeQuery = true)
    void statisAccount(long uid);

}
