package com.newframe.repositories.dataSlave.order;

import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderHirer;
import com.newframe.utils.query.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderHirerSlave extends BaseRepository<OrderHirer,Long>  {
    /**
     * @author kfm
     * 查询订单融资次数
     * @param orderId 订单id
     * @return 租赁次数
     */
    @Query("select dispatchTimes as times  from OrderHirer where orderId = ?1")
    Integer getOrderFinancingTimes(Long orderId);
}
