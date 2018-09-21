package com.newframe.services.block.impl;

import com.newframe.blockchain.entity.ResponseChain;
import com.newframe.blockchain.util.BlockChainRepository;
import com.newframe.dto.block.*;
import com.newframe.entity.user.UserContract;
import com.newframe.services.block.BlockChain;
import com.newframe.services.userbase.UserContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import types.EzTransfer;

import java.util.List;

/**
 * @author WangBin
 */
@Service
public class BlockChainImpl implements BlockChain {

    @Autowired
    private BlockChainRepository blockChainRepository;
    @Value("${blockchain.pubkey}")
    private String adminPubkey;
    @Value("${blockchain.prikey}")
    private String adminPriKey;
    @Autowired
    private UserContractService userContractService;

    /**
     * 资金方申请上链
     *
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    @Override
    public ResponseChain funderApply(Long uid, String pubKey, String merchantName) {

        EzTransfer.EzFunderApply.Builder builder = EzTransfer.EzFunderApply.newBuilder()
                .setUid(uid)
                .setPubKey(pubKey)
                .setMerchantName(merchantName);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setFunderApply(builder)
                .setTy(EzTransfer.EzActionType.AT_FUNDER_APPLY);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 出租方申请成功
     *
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    @Override
    public ResponseChain lessorApply(Long uid, String pubKey, String merchantName) {
        EzTransfer.EzLessorApply.Builder builder = EzTransfer.EzLessorApply.newBuilder()
                .setUid(uid)
                .setPubKey(pubKey)
                .setMerchantName(merchantName);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setLessorApply(builder)
                .setTy(EzTransfer.EzActionType.AT_LESSOR_APPLY);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 供应商申请成功
     *
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    @Override
    public ResponseChain supplierApply(Long uid, String pubKey, String merchantName) {
        EzTransfer.EzSupplierApply.Builder builder = EzTransfer.EzSupplierApply.newBuilder()
                .setUid(uid)
                .setPubKey(pubKey)
                .setMerchantName(merchantName);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setSupplierApply(builder)
                .setTy(EzTransfer.EzActionType.AT_SUPPLIER_APPLY);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 一级租赁商申请成功
     *
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    @Override
    public ResponseChain t1MerchantApply(Long uid, String pubKey, String merchantName) {
        EzTransfer.EzT1MerchantApply.Builder builder = EzTransfer.EzT1MerchantApply.newBuilder()
                .setUid(uid)
                .setPubKey(pubKey)
                .setMerchantName(merchantName);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setT1MerchantApply(builder)
                .setTy(EzTransfer.EzActionType.AT_T1_MERCHANT_APPLY);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 二级租赁商申请成功
     *
     * @param belongTo 隶属的一级租赁商uid
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    @Override
    public ResponseChain t2MerchantApply(Long belongTo, Long uid, String pubKey, String merchantName) {
        UserContract contract = userContractService.findOne(belongTo);
        EzTransfer.EzT2MerchantApply.Builder builder = EzTransfer.EzT2MerchantApply.newBuilder()
                .setBelongTo(belongTo)
                .setUid(uid)
                .setPubKey(pubKey)
                .setMerchantName(merchantName);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setT2MerchantApply(builder)
                .setTy(EzTransfer.EzActionType.AT_T2_MERCHANT_APPLY);
        try {
            return blockChainRepository.sendTransaction(contract.getPublickey(), contract.getPrivatekey(), action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 删除小B
     *
     * @param t1MerchantUid 大Buid
     * @param t2MerchantUid 小Buid
     * @return
     */
    @Override
    public ResponseChain rmT2Merchant(Long t1MerchantUid, Long t2MerchantUid) {
        UserContract contract = userContractService.findOne(t1MerchantUid);
        EzTransfer.EzRmT2Merchant.Builder builder = EzTransfer.EzRmT2Merchant.newBuilder()
                .setT1MerchantUid(t1MerchantUid)
                .setT2MerchantUid(t2MerchantUid);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setRmT2Merchant(builder)
                .setTy(EzTransfer.EzActionType.AT_RM_T2_MERCHANT);
        try {
            return blockChainRepository.sendTransaction(contract.getPublickey(), contract.getPrivatekey(), action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 融资购机
     *
     * @param financeApplyDTO
     * @return
     */
    @Override
    public ResponseChain financeApply(FinanceApply financeApplyDTO) {
        UserContract contract = userContractService.findOne(financeApplyDTO.getMerchantUid());
        EzTransfer.EzFinanceApply.Builder builder = EzTransfer.EzFinanceApply.newBuilder()
                .setOrderNum(financeApplyDTO.getOrderId())
                .setMerchantUid(financeApplyDTO.getMerchantUid())
                .setFinancingAmount(financeApplyDTO.getFinancingAmount().floatValue())
                .setFinancingTerm(financeApplyDTO.getFinancingTerm())
                .setFunderUid(financeApplyDTO.getFunderUid())
                .setSupplierUid(financeApplyDTO.getSupplierUid())
                .setApplyTime(financeApplyDTO.getApplyTime())
                .setLesseeOrder(setLesseeOrder(financeApplyDTO));
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setFinanceApply(builder)
                .setTy(EzTransfer.EzActionType.AT_FINANCE_APPLY);
        try {
            return blockChainRepository.sendTransaction(contract.getPublickey(), contract.getPrivatekey(), action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 资金方拒绝订单
     *
     * @param orderNum 订单编号
     * @param funderUid 资金方uid
     * @return
     */
    @Override
    public ResponseChain financeRefuse(Long orderNum, Long funderUid) {
        UserContract contract = userContractService.findOne(funderUid);
        EzTransfer.EzFinanceRefuse.Builder builder = EzTransfer.EzFinanceRefuse.newBuilder()
                .setOrderNum(String.valueOf(orderNum))
                .setFunderUid(funderUid);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setFinanceRefuse(builder)
                .setTy(EzTransfer.EzActionType.AT_FINANCE_REFUSE);
        try {
            return blockChainRepository.sendTransaction(contract.getPublickey(), contract.getPrivatekey(), action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 资金方放款给供应商
     *
     * @param fundSupplier
     * @return
     */
    @Override
    public ResponseChain fundSupplier(FundSupplier fundSupplier) {
        UserContract contract = userContractService.findOne(fundSupplier.getFunderUid());
        EzTransfer.EzFundSupplier.Builder builder = EzTransfer.EzFundSupplier.newBuilder()
                .setOrderNum(String.valueOf(fundSupplier.getOrderNum()))
                .setFunderUid(fundSupplier.getFunderUid())
                .setMethod(EzTransfer.EzFinancingMethod.FM_ASSIGNMENT_OF_DEBT)
                .setSupplierUid(fundSupplier.getSupplierUid())
                .setFundVoucher(fundSupplier.getFundVoucher())
                .setLoanTime(fundSupplier.getLoanTime());
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setFundSupplier(builder)
                .setTy(EzTransfer.EzActionType.AT_FUND_SUPPLIER);
        try {
            return blockChainRepository.sendTransaction(contract.getPublickey(), contract.getPrivatekey(), action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 供应商发货
     *
     * @param supplierDeliver
     * @return
     */
    @Override
    public ResponseChain supplierDeliver(SupplierDeliver supplierDeliver) {
        UserContract contract = userContractService.findOne(supplierDeliver.getSupplierUid());
        EzTransfer.EzSupplierDeliver.Builder builder = EzTransfer.EzSupplierDeliver.newBuilder()
                .setOrderNum(String.valueOf(supplierDeliver.getOrderNum()))
                .setSupplierUid(supplierDeliver.getSupplierUid())
                .setExpressInfo(setEzExpressInfo(supplierDeliver));
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setSupplierDeliver(builder)
                .setTy(EzTransfer.EzActionType.AT_SUPPLIER_DELIVER);
        try {
            return blockChainRepository.sendTransaction(contract.getPublickey(), contract.getPrivatekey(), action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 融资购机订单确认收货
     *
     * @param orderNum 订单id
     * @param trackingNum 物流单号
     * @param confirmTime 确认收货时间
     * @return
     */
    @Override
    public ResponseChain confirmForFinance(Long orderNum, String trackingNum, Integer confirmTime) {
        // TODO 暂无
        EzTransfer.EzConfirmForFinance.Builder builder = EzTransfer.EzConfirmForFinance.newBuilder()
                .setOrderNum(String.valueOf(orderNum))
                .setTrackingNum(trackingNum)
                .setConfirmTime(confirmTime);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setConfirmForFinance(builder)
                .setTy(EzTransfer.EzActionType.AT_CONFIRM_FOR_FINANCE);
        return null;
    }

    /**
     * 租赁商向出租方租机
     *
     * @param rentalApply
     * @return
     */
    @Override
    public ResponseChain rentalApply(RentalApply rentalApply) {
        UserContract contract = userContractService.findOne(rentalApply.getMerchantUid());
        EzTransfer.EzRentalApply.Builder builder = EzTransfer.EzRentalApply.newBuilder()
                .setOrderNum(String.valueOf(rentalApply.getOrderNum()))
                .setLessorUid(rentalApply.getLessorUid())
                .setMerchantUid(rentalApply.getMerchantUid())
                .setPaymentMethod(rentalApply.getPaymentMethod())
                .setRentalAmount(rentalApply.getRentalAmount().floatValue())
                .setRentalTerm(rentalApply.getRentalTerm())
                .setApplyTime(rentalApply.getApplyTime());
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setRentalApply(builder)
                .setTy(EzTransfer.EzActionType.AT_RENTAL_APPLY);
        try {
            return blockChainRepository.sendTransaction(contract.getPublickey(), contract.getPrivatekey(), action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 出租方拒绝订单
     *
     * @param orderNum  订单号
     * @param lessorUid 出租方uid
     * @return
     */
    @Override
    public ResponseChain rentalRefuse(Long orderNum, Long lessorUid) {
        UserContract contract = userContractService.findOne(lessorUid);
        EzTransfer.EzRentalRefuse.Builder builder = EzTransfer.EzRentalRefuse.newBuilder()
                .setOrderNum(String.valueOf(orderNum))
                .setLessorUid(lessorUid);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setRentalRefuse(builder)
                .setTy(EzTransfer.EzActionType.AT_RENTAL_REFUSE);
        try {
            return blockChainRepository.sendTransaction(contract.getPublickey(), contract.getPrivatekey(), action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 租赁商打款给出租方
     *
     * @param orderNum    订单id
     * @param lessorUid   出租方uid
     * @param merchantUid 租赁商uid
     * @param payTime     打款时间
     * @return
     */
    @Override
    public ResponseChain payLessor(Long orderNum, Long lessorUid, Long merchantUid, Integer payTime) {
        UserContract contract = userContractService.findOne(merchantUid);
        EzTransfer.EzPayLessor.Builder builder = EzTransfer.EzPayLessor.newBuilder()
                .setOrderNum(String.valueOf(orderNum))
                .setLessorUid(lessorUid)
                .setMerchantUid(merchantUid)
                .setPayTime(payTime);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setPayLessor(builder)
                .setTy(EzTransfer.EzActionType.AT_PAY_LESSOR);
        try {
            return blockChainRepository.sendTransaction(contract.getPublickey(), contract.getPrivatekey(), action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 出租方发货
     *
     * @param lessorDeliver
     * @return
     */
    @Override
    public ResponseChain lessorDeliver(LessorDeliver lessorDeliver) {
        UserContract contract = userContractService.findOne(lessorDeliver.getLessorUid());
        EzTransfer.EzLessorDeliver.Builder builder = EzTransfer.EzLessorDeliver.newBuilder()
                .setOrderNum(String.valueOf(lessorDeliver.getOrderNum()))
                .setLessorUid(lessorDeliver.getLessorUid())
                .setExpressInfo(setEzExpressInfo(lessorDeliver));
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setLessorDeliver(builder)
                .setTy(EzTransfer.EzActionType.AT_LESSOR_DELIVER);
        try {
            return blockChainRepository.sendTransaction(contract.getPublickey(), contract.getPrivatekey(), action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 租机订单确认收货
     *
     * @param orderNum 订单id
     * @param trackingNum 物流编号
     * @param confirmTime 确认收货时间
     * @return
     */
    @Override
    public ResponseChain confirmForRental(Long orderNum, String trackingNum, Integer confirmTime) {
        EzTransfer.EzConfirmForRental.Builder builder = EzTransfer.EzConfirmForRental.newBuilder()
                .setOrderNum(String.valueOf(orderNum))
                .setTrackingNum(trackingNum)
                .setConfirmTime(confirmTime);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setConfirmForRental(builder)
                .setTy(EzTransfer.EzActionType.AT_CONFIRM_FOR_RENTAL);
        return null;
    }

    /**
     * 添加失信记录
     *
     * @param addDiscreditRecord
     * @return
     */
    @Override
    public ResponseChain addDiscreditRecord(AddDiscreditRecord addDiscreditRecord) {
        EzTransfer.EzAddDiscreditRecord.Builder builder = EzTransfer.EzAddDiscreditRecord.newBuilder()
                .setUid(addDiscreditRecord.getUid())
                .setUserTypeValue(addDiscreditRecord.getRoleId())
                .setDiscreditRecordType(addDiscreditRecord.getDiscreditRecordType())
                .setBadDebtsAmount(addDiscreditRecord.getBadDebtsAmount().floatValue());
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setAddDiscreditRecord(builder)
                .setTy(EzTransfer.EzActionType.AT_ADD_DISCREDIT_RECORD);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 添加白名单
     *
     * @param uids uid数组
     * @return
     */
    @Override
    public ResponseChain addWhitelists(List<Long> uids) {
        EzTransfer.EzAddWhitelists.Builder builder = EzTransfer.EzAddWhitelists.newBuilder()
                .addAllUid(uids);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setAddWhitelists(builder)
                .setTy(EzTransfer.EzActionType.AT_ADD_WHITELISTS);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 删除白名单
     *
     * @param uids uid数组
     * @return
     */
    @Override
    public ResponseChain rmWhitelists(List<Long> uids) {
        EzTransfer.EzAddWhitelists.Builder builder = EzTransfer.EzAddWhitelists.newBuilder()
                .addAllUid(uids);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setAddWhitelists(builder)
                .setTy(EzTransfer.EzActionType.AT_RM_WHITELISTS);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 添加黑名单
     *
     * @param uids   uid数组
     * @param roleId 角色id
     * @return
     */
    @Override
    public ResponseChain addBlacklists(List<Long> uids, Integer roleId) {
        EzTransfer.EzAddBlacklists.Builder builder = EzTransfer.EzAddBlacklists.newBuilder()
                .addAllUid(uids)
                .setUserTypeValue(roleId);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder()
                .setAddBlacklists(builder)
                .setTy(EzTransfer.EzActionType.AT_ADD_BLACKLISTS);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 封装 第三方订单
     *
     * @param lesseeOrder
     * @return
     */
    private EzTransfer.EzLesseeOrder.Builder setLesseeOrder(LesseeOrder lesseeOrder) {
        return EzTransfer.EzLesseeOrder.newBuilder()
                .setOrderNum(lesseeOrder.getOrderId())
                .setApplyTime(lesseeOrder.getOrderTime())
                .setProdDetail(setEzProdDetail(lesseeOrder))
                .setPhoneNum(lesseeOrder.getPhoneNum())
                .setRealName(lesseeOrder.getRealName());
    }

    /**
     * 封装 商品
     *
     * @param prodDetail
     * @return
     */
    private EzTransfer.EzProdDetail.Builder setEzProdDetail(ProdDetail prodDetail) {
        return EzTransfer.EzProdDetail.newBuilder()
                .setProdBrand(prodDetail.getProdBrand())
                .setProdName(prodDetail.getProdName())
                .setProdColor(prodDetail.getProdColor())
                .setProdStorage(prodDetail.getProdStorage())
                .setLeaseTerm(prodDetail.getLeaseTerm())
                .setProdAmount(prodDetail.getProdAmount());
    }


    private EzTransfer.EzExpressInfo.Builder setEzExpressInfo(ExpressInfo expressInfo) {
        return EzTransfer.EzExpressInfo.newBuilder()
                .setTrackingNum(expressInfo.getTrackingNum())
                .setIMEI(expressInfo.getIMEI())
                .setExpressCoName(expressInfo.getExpressCoName())
                .setDeliveryTime(expressInfo.getDeliveryTime())
                .setConfirmTime(expressInfo.getConfirmTime());
    }
}
