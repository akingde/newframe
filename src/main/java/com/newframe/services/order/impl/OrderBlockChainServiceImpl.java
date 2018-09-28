package com.newframe.services.order.impl;

import com.newframe.blockchain.entity.ResponseChain;
import com.newframe.common.exception.MobileException;
import com.newframe.dto.block.*;
import com.newframe.entity.order.*;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.services.block.BlockChainService;
import com.newframe.services.order.OrderBlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import types.EzTransfer;

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
        financeApply.setIdNum(orderRenter.getUserIdNumber());
        financeApply.setLeaseTerm(orderRenter.getNumberOfPayments());
        financeApply.setProdAmount(1);
        financeApply.setOrderId(String.valueOf(orderRenter.getOrderId()));
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

    @Override
    public void financeRefuse(Long orderId, Long funderId){
        ResponseChain responseChain = blockChainService.financeRefuse(orderId,funderId);
        if(responseChain == null || !responseChain.isSuccess()){
            throw new MobileException(RequestResultEnum.MODIFY_ERROR);
        }
    }

    @Override
    public void fundSupplier(OrderFunder orderFunder, OrderSupplier orderSupplier, OrderFunderEvidence evidence){
        FundSupplier fundSupplier = new FundSupplier();
        fundSupplier.setFunderUid(orderFunder.getFunderId());
        if(evidence != null){
            fundSupplier.setFundVoucher(evidence.getEvidenceUrl());
        }
        fundSupplier.setLoanTime(orderSupplier.getCtime());
        fundSupplier.setOrderNum(orderFunder.getOrderId());
        fundSupplier.setSupplierUid(orderSupplier.getSupplierId());
        ResponseChain responseChain = blockChainService.fundSupplier(fundSupplier);
        if(responseChain == null || !responseChain.isSuccess()){
            throw new MobileException(RequestResultEnum.MODIFY_ERROR);
        }
    }

    @Override
    public void supplierDeliver(OrderSupplier orderSupplier){
        SupplierDeliver deliver = new SupplierDeliver();
        deliver.setOrderNum(orderSupplier.getOrderId());
        deliver.setSupplierUid(orderSupplier.getSupplierId());
        Long applyTime = (System.currentTimeMillis()/1000);
        deliver.setConfirmTime(applyTime.intValue());
        deliver.setDeliveryTime(orderSupplier.getExpressTime().intValue());
        deliver.setExpressCoName(orderSupplier.getExpressCompany());
        deliver.setIMEI(orderSupplier.getSerialNumber());
        deliver.setTrackingNum(orderSupplier.getExpressNumber());
        ResponseChain responseChain = blockChainService.supplierDeliver(deliver);
        if(responseChain == null || !responseChain.isSuccess()){
            throw new MobileException(RequestResultEnum.MODIFY_ERROR);
        }
    }

    @Override
    public void rentApply(OrderRenter orderRenter, OrderHirer orderHirer){
        RentalApply rentalApply = new RentalApply();
        rentalApply.setApplyTime(orderHirer.getCtime());
        rentalApply.setLessorUid(orderHirer.getLessorId());
        rentalApply.setMerchantUid(orderRenter.getRenterId());
        rentalApply.setOrderNum(orderRenter.getOrderId());
        if(orderHirer.getPatternPayment().equals(EzTransfer.EzPaymentMethod.PM_LUMP_SUM.getNumber())){
            rentalApply.setPaymentMethod(EzTransfer.EzPaymentMethod.PM_LUMP_SUM);
        }
        if(orderHirer.getPatternPayment().equals(EzTransfer.EzPaymentMethod.PM_INSTALLMENT.getNumber())){
            rentalApply.setPaymentMethod(EzTransfer.EzPaymentMethod.PM_INSTALLMENT);
        }
        rentalApply.setRentalAmount(orderHirer.getOrderAmount());
        rentalApply.setRentalTerm(orderHirer.getNumberOfPeriods());
        rentalApply.setOrderId(orderHirer.getPartnerOrderId());
        rentalApply.setOrderTime(orderRenter.getCtime());
        rentalApply.setPhoneNum(orderRenter.getUserMobile());
        rentalApply.setRealName(orderRenter.getUserRealname());
        rentalApply.setIdNum(orderRenter.getUserIdNumber());
        rentalApply.setProdBrand(orderRenter.getProductBrand());
        rentalApply.setProdName(orderRenter.getProductName());
        rentalApply.setProdColor(orderRenter.getProductColor());
        rentalApply.setProdStorage(orderRenter.getProductRandomMemory()+"G + "+orderRenter.getProductStorage()+"G");
        rentalApply.setLeaseTerm(orderRenter.getNumberOfPayments());
        rentalApply.setProdAmount(1);
        ResponseChain responseChain = blockChainService.rentalApply(rentalApply);
        if(responseChain == null || !responseChain.isSuccess()){
            throw new MobileException(RequestResultEnum.MODIFY_ERROR);
        }
    }

    @Override
    public void rentalRefuse(Long orderId, Long lessorId){
        ResponseChain responseChain = blockChainService.rentalRefuse(orderId,lessorId);
        if(responseChain == null || !responseChain.isSuccess()){
            throw new MobileException(RequestResultEnum.MODIFY_ERROR);
        }
    }

    @Override
    public void payLessor(OrderRenter orderRenter,OrderHirer orderHirer){
        Long applyTime = (System.currentTimeMillis()/1000);
        ResponseChain responseChain = blockChainService.payLessor(orderRenter.getOrderId(),orderHirer.getLessorId(),orderRenter.getRenterId(),applyTime.intValue());
        if(responseChain == null || !responseChain.isSuccess()){
            throw new MobileException(RequestResultEnum.MODIFY_ERROR);
        }
    }

    @Override
    public void lessorDeliver(OrderHirer orderHirer,HirerDeliver hirerDeliver){

        LessorDeliver lessorDeliver = new LessorDeliver();
        lessorDeliver.setLessorUid(hirerDeliver.getLessorId());
        lessorDeliver.setOrderNum(orderHirer.getOrderId());
        Long applyTime = (System.currentTimeMillis()/1000);
        lessorDeliver.setConfirmTime(applyTime.intValue());
        lessorDeliver.setDeliveryTime(hirerDeliver.getExpressTime().intValue());
        lessorDeliver.setExpressCoName(hirerDeliver.getExpressName());
        lessorDeliver.setIMEI(hirerDeliver.getSerialNumber());
        lessorDeliver.setTrackingNum(hirerDeliver.getExpressNumber());
        ResponseChain responseChain = blockChainService.lessorDeliver(lessorDeliver);
        if(responseChain == null || !responseChain.isSuccess()){
            throw new MobileException(RequestResultEnum.MODIFY_ERROR);
        }
    }

}
