package com.newframe.services.order;

import com.newframe.dto.OperationResult;
import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderRenter;
import com.newframe.entity.order.OrderSupplier;

/**
 * @author kfm
 * @date 2018.09.17 9:57
 */
public interface OrderAccountOperationService {
    Boolean financing(OrderRenter orderRenter, OrderFunder orderFunder);

    Boolean releaseMarginBalance(OrderRenter orderRenter, OrderFunder orderFunder);

    OperationResult<Boolean> onlineLoan(OrderRenter orderRenter, OrderFunder orderFunder, OrderSupplier orderSupplier);
}
