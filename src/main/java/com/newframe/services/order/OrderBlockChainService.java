package com.newframe.services.order;

import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderRenter;

/**
 * 订单相关的区块链操作服务类
 * @author kfm
 * @date 2018.09.26 11:34
 */
public interface OrderBlockChainService {

    /**
     * 融资申请上链
     * @param orderRenter 租赁商订单
     * @param orderFunder 资金方订单
     */
    void financeApply(OrderRenter orderRenter, OrderFunder orderFunder);


}
