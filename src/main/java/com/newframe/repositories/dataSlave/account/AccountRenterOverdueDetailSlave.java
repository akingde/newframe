package com.newframe.repositories.dataSlave.account;


import com.newframe.entity.account.AccountRenterOverdueDetail;
import com.newframe.entity.account.AccountRenterRentDetail;
import com.newframe.utils.query.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>
 *
 * </p>
 *
 * @since 2018-08-29
 */
public interface AccountRenterOverdueDetailSlave extends BaseRepository<AccountRenterOverdueDetail, Long> {

    /**
     * 统计租赁商逾期次数
     * @param uid uid
     * @return 逾期次数
     */
    @Query("select count(*) as times from AccountRenterOverdueDetail where uid = ?1")
    Integer getRentOverdueTimes(Long uid);
}
