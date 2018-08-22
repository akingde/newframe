package com.newframe.repositories.dataSlave.order;

import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderRenter;
import com.newframe.utils.query.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderFunderSlave extends BaseRepository<OrderFunder,Long>  {

    @Query("select count(1) as times  from OrderFunder where orderFunderPK.orderId = ?1")
    Long getOrderFinancingTimes(Long orderId);

    @Query("from OrderFunder where orderFunderPK.orderId = ?1")
    List<OrderFunder> getOrderByOrderId(Long orderId);
}
