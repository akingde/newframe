package com.newframe.repositories.dataSlave.order;

import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderRenter;
import com.newframe.utils.query.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderFunderSlave extends BaseRepository<OrderFunder,Long>  {
    /**
     * 查询订单融资次数
     * @param orderId 订单id
     * @return 融资次数
     */
    @Query("select dispatchTimes as times  from OrderFunder where orderId = ?1")
    Integer getOrderFinancingTimes(Long orderId);
}