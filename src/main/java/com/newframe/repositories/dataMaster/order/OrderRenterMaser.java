package com.newframe.repositories.dataMaster.order;

import com.newframe.entity.order.OrderRenter;
import com.newframe.utils.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author kfm
 * 租赁商订单操作
 */
public interface OrderRenterMaser extends BaseRepository<OrderRenter,Long> {
    /**
     *
     * 修改订单状态
     * @author kfm
     * @date 2018年8月16日 14点35分
     * @param orderStatus
     * @param orderId
     * @return
     */
    @Modifying
    @Query("update OrderRenter set orderStatus = ?1 where orderId = ?2")
    int updateOrderStatus(Integer orderStatus,Long orderId);
}
