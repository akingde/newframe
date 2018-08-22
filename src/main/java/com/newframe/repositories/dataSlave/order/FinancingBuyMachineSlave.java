package com.newframe.repositories.dataSlave.order;

import com.newframe.entity.order.FinancingBuyMachine;
import com.newframe.utils.query.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author kfm
 */
public interface FinancingBuyMachineSlave extends BaseRepository<FinancingBuyMachine,Long> {
    /**
     * 查询订单融资次数
     * @param orderId 订单id
     * @return 融资次数
     */
    @Query("select dispatchTimes as times  from FinancingBuyMachine where orderId = ?1")
    Integer getOrderFinancingTimes(Long orderId);
}
