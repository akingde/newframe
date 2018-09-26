package com.newframe.services.order;

import com.newframe.entity.order.*;

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

    /**
     * 资金方拒绝融资请求
     * @param orderId 订单id
     * @param funderId 资金方id
     */
    void financeRefuse(Long orderId, Long funderId);

    /**
     * 资金方放款给供应商
     * @param orderFunder 资金方订单
     * @param orderSupplier 供应商订单
     * @param evidence 放款凭证，如果未上传图片为null
     */
    void fundSupplier(OrderFunder orderFunder, OrderSupplier orderSupplier, OrderFunderEvidence evidence);

    /**
     * 供应商发货
     * @param orderSupplier 供应商订单
     */
    void supplierDeliver(OrderSupplier orderSupplier);

    /**
     * 租赁商租机申请
     * @param orderRenter 租赁商订单
     * @param orderHirer 出租方订单
     */
    void rentApply(OrderRenter orderRenter, OrderHirer orderHirer);

    /**
     * 出租方订单被拒绝
     * @param orderId 订单id
     * @param lessorId 出租方id
     */
    void rentalRefuse(Long orderId, Long lessorId);

    /**
     * 租赁商付款给出租方
     * @param orderRenter 租赁商订单
     * @param orderHirer 出租方订单
     */
    void payLessor(OrderRenter orderRenter, OrderHirer orderHirer);

    /**
     * 出租方发货
     * @param orderHirer 出租方订单
     * @param hirerDeliver 出租方发货信息
     */
    void lessorDeliver(OrderHirer orderHirer, HirerDeliver hirerDeliver);
}
