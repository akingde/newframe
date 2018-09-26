package com.newframe.services.order.impl;

import com.newframe.blockchain.entity.ResponseChain;
import com.newframe.common.exception.MobileException;
import com.newframe.dto.block.FinanceApply;
import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderRenter;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.services.block.BlockChainService;
import com.newframe.services.order.OrderBlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单相关的区块链操作实现类
 * @author kfm
 * @date 2018.09.26 11:34
 */
@Service
public class OrderBlockChainServiceImpl implements OrderBlockChainService {
    @Autowired
    BlockChainService blockChainService;

    @Override
    public void financeApply(OrderRenter orderRenter, OrderFunder orderFunder) {
        // 融资请求上链
        FinanceApply financeApply = new FinanceApply();
        Long applyTime = (System.currentTimeMillis()/1000);
        financeApply.setApplyTime(applyTime.intValue());
        financeApply.setFinancingAmount(orderFunder.getFinancingAmount());
        financeApply.setFinancingTerm(orderFunder.getNumberOfPeriods());
        financeApply.setFunderUid(orderFunder.getFunderId());
        financeApply.setMerchantUid(orderRenter.getRenterId());
        financeApply.setOrderNum(orderRenter.getOrderId());
        financeApply.setSupplierUid(orderFunder.getSupplierId());
        financeApply.setIdNum(orderRenter.getUserMobile());
        financeApply.setLeaseTerm(orderRenter.getNumberOfPayments());
        financeApply.setProdAmount(1);
        financeApply.setOrderId(orderRenter.getPartnerOrderId());
        financeApply.setOrderTime(orderRenter.getCtime());
        financeApply.setPhoneNum(orderRenter.getUserMobile());
        financeApply.setProdBrand(orderRenter.getProductBrand());
        financeApply.setProdColor(orderRenter.getProductColor());
        financeApply.setProdName(orderRenter.getProductName());
        financeApply.setProdStorage(orderRenter.getProductRandomMemory()+"G + "+orderRenter.getProductStorage()+"G");
        financeApply.setRealName(orderRenter.getUserRealname());
        financeApply.setLeaseTerm(orderRenter.getNumberOfPayments());
        ResponseChain responseChain = blockChainService.financeApply(financeApply);
        if(responseChain == null || !responseChain.isSuccess()){
            throw new MobileException(RequestResultEnum.MODIFY_ERROR);
        }
    }
}
