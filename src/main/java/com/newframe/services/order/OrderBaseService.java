package com.newframe.services.order;

import com.newframe.enums.order.MessagePushEnum;

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
     * 消息推送
     * @param receiverId 接收者uid
     * @param orderId 订单id
     * @param associatedOrderId 关联订单id
     * @param info 消息信息
     */
    void messagePush(Long receiverId, Long orderId, String associatedOrderId, MessagePushEnum info);
}
