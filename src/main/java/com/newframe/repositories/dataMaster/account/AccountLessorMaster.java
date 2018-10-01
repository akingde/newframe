package com.newframe.repositories.dataMaster.account;


import com.newframe.entity.account.AccountLessor;
import com.newframe.utils.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * <p>
 * 出租方实物资产 服务类
 * </p>
 *
 * @author zww
 * @since 2018-08-29
 */
public interface AccountLessorMaster extends BaseRepository<AccountLessor,Long> {
    @Transactional
    @Modifying
    @Query(value = "call pro_account_lessor(?1)", nativeQuery = true)
    void statisAccount(long uid);
}
