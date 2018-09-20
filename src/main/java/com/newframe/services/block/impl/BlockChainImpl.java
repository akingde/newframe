package com.newframe.services.block.impl;

import com.newframe.blockchain.entity.ResponseChain;
import com.newframe.blockchain.util.BlockChainRepository;
import com.newframe.dto.block.EzFinanceApply;
import com.newframe.dto.block.EzLesseeOrder;
import com.newframe.dto.block.EzProdDetail;
import com.newframe.entity.user.UserContract;
import com.newframe.services.block.BlockChain;
import com.newframe.services.userbase.UserContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import types.EzTransfer;

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
     * @param merchantName
     * @return
     */
    @Override
    public ResponseChain funderApply(Long uid, String pubKey, String merchantName){

        EzTransfer.EzFunderApply.Builder builder = EzTransfer.EzFunderApply.newBuilder();
        builder.setUid(uid);
        builder.setPubKey(pubKey);
        builder.setMerchantName(merchantName);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder();
        action.setFunderApply(builder);
        action.setTy(EzTransfer.EzActionType.AT_FUNDER_APPLY);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 出租方申请成功
     *
     * @param uid
     * @param pubKey       用户公钥
     * @param merchantName
     * @return
     */
    @Override
    public ResponseChain lessorApply(Long uid, String pubKey, String merchantName) {
        EzTransfer.EzLessorApply.Builder builder = EzTransfer.EzLessorApply.newBuilder();
        builder.setUid(uid);
        builder.setPubKey(pubKey);
        builder.setMerchantName(merchantName);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder();
        action.setLessorApply(builder);
        action.setTy(EzTransfer.EzActionType.AT_LESSOR_APPLY);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 供应商申请成功
     *
     * @param uid
     * @param pubKey
     * @param merchantName
     * @return
     */
    @Override
    public ResponseChain supplierApply(Long uid, String pubKey, String merchantName) {
        EzTransfer.EzSupplierApply.Builder builder = EzTransfer.EzSupplierApply.newBuilder();
        builder.setUid(uid);
        builder.setPubKey(pubKey);
        builder.setMerchantName(merchantName);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder();
        action.setSupplierApply(builder);
        action.setTy(EzTransfer.EzActionType.AT_SUPPLIER_APPLY);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 一级租赁商申请成功
     *
     * @param uid
     * @param pubKey
     * @param merchantName
     * @return
     */
    @Override
    public ResponseChain t1MerchantApply(Long uid, String pubKey, String merchantName) {
        EzTransfer.EzT1MerchantApply.Builder builder = EzTransfer.EzT1MerchantApply.newBuilder();
        builder.setUid(uid);
        builder.setPubKey(pubKey);
        builder.setMerchantName(merchantName);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder();
        action.setT1MerchantApply(builder);
        action.setTy(EzTransfer.EzActionType.AT_T1_MERCHANT_APPLY);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 二级租赁商申请成功
     *
     * @param belongTo
     * @param uid
     * @param pubKey
     * @param merchantName
     * @return
     */
    @Override
    public ResponseChain t2MerchantApply(Long belongTo, Long uid, String pubKey, String merchantName) {
        UserContract contract = userContractService.findOne(belongTo);
        EzTransfer.EzT2MerchantApply.Builder builder = EzTransfer.EzT2MerchantApply.newBuilder();
        builder.setBelongTo(belongTo);
        builder.setUid(uid);
        builder.setPubKey(pubKey);
        builder.setMerchantName(merchantName);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder();
        action.setT2MerchantApply(builder);
        action.setTy(EzTransfer.EzActionType.AT_T2_MERCHANT_APPLY);
        try {
            return blockChainRepository.sendTransaction(contract.getPublickey(), contract.getPrivatekey(), action.build());
        }catch (Exception e){
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
    public ResponseChain financeApply(EzFinanceApply financeApplyDTO) {
        EzTransfer.EzFinanceApply.Builder builder = EzTransfer.EzFinanceApply.newBuilder();
        builder.setLesseeOrder(setLesseeOrder(financeApplyDTO));
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder();
        action.setFinanceApply(builder);
        action.setTy(EzTransfer.EzActionType.AT_T2_MERCHANT_APPLY);
        return null;
    }

    /**
     * 封装 第三方订单
     * @param ezLesseeOrder
     * @return
     */
    private types.EzTransfer.EzLesseeOrder.Builder setLesseeOrder(EzLesseeOrder ezLesseeOrder){
        types.EzTransfer.EzLesseeOrder.Builder builder = types.EzTransfer.EzLesseeOrder.newBuilder();
        builder.setOrderNum(Long.valueOf(ezLesseeOrder.getOrderId()));
        builder.setApplyTime(ezLesseeOrder.getOrderTime());
        builder.setProdDetail(setEzProdDetail(ezLesseeOrder));
        builder.setPhoneNum(Long.valueOf(ezLesseeOrder.getPhoneNum()));
        builder.setRealname(ezLesseeOrder.getRealName());
        return builder;
    }

    /**
     * 封装 商品
     * @param ezProdDetail
     * @return
     */
    private types.EzTransfer.EzProdDetail.Builder setEzProdDetail(EzProdDetail ezProdDetail){
        types.EzTransfer.EzProdDetail.Builder builder = types.EzTransfer.EzProdDetail.newBuilder();
        builder.setProdBrand(ezProdDetail.getProdBrand());
        builder.setProdName(ezProdDetail.getProdName());
        builder.setProdColor(ezProdDetail.getProdColor());
        builder.setProdStorage(ezProdDetail.getProdStorage());
        builder.setLeaseTerm(ezProdDetail.getLeaseTerm());
        builder.setProdAmount(ezProdDetail.getProdAmount());
        return builder;
    }
}
