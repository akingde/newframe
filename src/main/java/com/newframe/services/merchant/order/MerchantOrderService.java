package com.newframe.services.merchant.order;

import com.newframe.dto.OperationResult;
import com.newframe.dto.merchant.order.MerchantOrderDTO;

/**
 * @author kfm
 * @date 2018.09.14 11:47
 */
public interface MerchantOrderService {
    OperationResult<Boolean> pushOrder(MerchantOrderDTO merchantOrder);
}
