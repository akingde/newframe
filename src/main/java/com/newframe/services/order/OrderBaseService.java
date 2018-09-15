package com.newframe.services.order;

import com.newframe.entity.order.OrderHirer;
import com.newframe.entity.order.OrderRenter;
import com.newframe.enums.order.MessagePushEnum;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * @author kfm
 * @date 2018.09.03 20:33
 */
public interface OrderBaseService {
    String getSupplierName(Long supplierId);

    String getRenterPhone(Long renterId);

    Integer getOrderFinancingTimes(Long orderId);

    Integer getOrderRentTimes(Long orderId);

    /**
     * 生成资金方收款计划
     * @param financingAmount 融资金额
     * @param tenancyTerm 融资期限
     * @param renterId 租赁商id
     * @param funderId 资金方id
     * @param orderId 订单id
     */
    void generateFundingSchedule(BigDecimal financingAmount, Integer tenancyTerm,
                                 Long renterId, Long funderId, Long orderId) throws ParseException;

    /**
     * 消息推送
     * @param receiverId 接收者uid
     * @param orderId 订单id
     * @param associatedOrderId 关联订单id
     * @param info 消息信息
     */
    void messagePush(Long receiverId, Long orderId, String associatedOrderId, MessagePushEnum info);

    /**
     * 租机完成后操作租赁账户
     * @param orderRenter 租赁商订单
     * @param orderHirer 出租方订单
     * @return 操作结果
     */
    boolean renterRentAccountOperation(OrderRenter orderRenter, OrderHirer orderHirer);
}
