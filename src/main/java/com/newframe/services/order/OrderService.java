package com.newframe.services.order;

import com.newframe.controllers.JsonResult;
import com.newframe.dto.order.request.QueryOrderDTO;

import java.math.BigDecimal;
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

    /**
     * 租赁商租机
     * @param uid  租赁商id
     * @param orderId 订单id
     * @param lessorId 租赁商选择的出租方id
     * @param tenancyTerm 租机期限
     * @param downPayment 首付租金
     * @param accidentBenefit 意外保险
     * @param patternPayment 支付方式
     * @return
     */
    JsonResult renterRent(Long uid, Long orderId, Long lessorId, Integer tenancyTerm, BigDecimal downPayment, BigDecimal accidentBenefit, Integer patternPayment);
}
