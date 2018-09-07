package com.newframe.services.order;

/**
 * @author kfm
 * @date 2018.09.03 20:33
 */
public interface OrderBaseService {
    String getSupplierName(Long supplierId);

    String getRenterPhone(Long renterId);

    Integer getOrderFinancingTimes(Long orderId);

    Integer getOrderRentTimes(Long orderId);
}
