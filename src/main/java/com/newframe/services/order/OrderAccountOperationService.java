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
    /**
     * 融资购机
     * 租赁账户可用余额中扣除保证金------>保证金 =（手机供应价-用户租机首付）×15%
     *
     * @return
     */
    OperationResult<Boolean> financing(OrderRenter orderRenter, OrderFunder orderFunder);
    /**
     * 退还保证金
     * 资金方拒绝审核和租赁商向资金方还款完成后退还保证金
     *
     * @return
     */
    OperationResult<Boolean> releaseMarginBalance(OrderRenter orderRenter, OrderFunder orderFunder);
    /**
     * 资金方线上放款
     * 租赁商账户可用余额中扣除用户租机首付；
     * 资金方账户扣除融资金额【手机的供应价-用户租机首付】；
     * （扣除金额全部转到供应商账户）
     * @return
     */
    OperationResult<Boolean> onlineLoan(OrderRenter orderRenter, OrderFunder orderFunder, OrderSupplier orderSupplier);
    /**
     * 资金方线下放款
     * 租赁商账户可用余额中扣除用户租机首付；
     * 资金方线下打款，不操作资金方账户；
     * （租赁商扣除金额转入供应商账户）
     * @return
     */
    OperationResult<Boolean> offlineLoan(OrderRenter orderRenter, OrderSupplier orderSupplier,OrderFunder orderFunder);
}
