package com.newframe.repositories.dataSlave.account;


import com.newframe.entity.account.AccountRenterFinancing;
import com.newframe.entity.account.AccountRenterFinancingMachine;
import com.newframe.utils.query.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @since 2018-08-29
 */
public interface AccountRenterFinancingSlave extends BaseRepository<AccountRenterFinancing, Long> {
    /**
     * 查询租赁商的融资金额
     * @param uid 租赁商
     * @return 融资金额
     */
    @Query("select sum(financingAmount) as financingA from AccountRenterFinancing where uid = ?1")
    BigDecimal getFinancingAmount(Long uid);
}
