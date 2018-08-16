package com.newframe.services.order;

import com.newframe.controllers.JsonResult;
import com.newframe.dto.order.request.QueryOrderDTO;

import java.util.List;

/**
 * 订单接口
 * @author kfm
 */
public interface OrderService {

    /**
     * 获取租赁商所有订单
     * @param param 查询参数
     * @param uid
     * @return jsonResult
     */
    JsonResult getRenterOrder(QueryOrderDTO param, Long uid);

    /**
     * 租赁商融资购机
     * @param uid
     * @param orderId
     * @param supplierId
     * @return
     */
    JsonResult renterFinancingBuy(Long uid, List<Long> orderId, Integer supplierId);
}
