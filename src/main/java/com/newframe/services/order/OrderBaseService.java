package com.newframe.services.order;

import com.newframe.dto.order.response.SupplierInfoDTO;
import com.newframe.entity.order.OrderFunder;
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

    String getRenterName(Long renterId);

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

    /**
     * 租机完成后操作租赁账户
     * @param orderRenter 租赁商订单
     * @param orderHirer 出租方订单
     * @return 操作结果
     */
    boolean renterRentAccountOperation(OrderRenter orderRenter, OrderHirer orderHirer);

    boolean renterFunderAccountOperation(OrderRenter orderRenter, OrderFunder orderFunder);

    /**
     * 计算手机的租赁价格
     * @param price 手机购买价
     * @param rate 年化收益率
     * @param numberOfPayment 租期
     * @return 每个月的租金
     */
    BigDecimal getRentPrice(BigDecimal price, BigDecimal rate, Integer numberOfPayment);

    void getSupplierInfo(SupplierInfoDTO supplierInfoDTO, BigDecimal supplyPrice, Integer periods);
}
