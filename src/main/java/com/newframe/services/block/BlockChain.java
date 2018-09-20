package com.newframe.services.block;

import com.newframe.blockchain.entity.ResponseChain;
import com.newframe.dto.block.EzFinanceApply;

/**
 * @author WangBin
 */
public interface BlockChain {

    /**
     * 资金方申请上链
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    ResponseChain funderApply(Long uid, String pubKey, String merchantName);

    /**
     * 出租方申请成功
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    ResponseChain lessorApply(Long uid, String pubKey, String merchantName);

    /**
     * 供应商申请成功
     * @param uid
     * @param pubKey
     * @param merchantName
     * @return
     */
    ResponseChain supplierApply(Long uid, String pubKey, String merchantName);

    /**
     * 一级租赁商申请成功
     * @param uid
     * @param pubKey
     * @param merchantName
     * @return
     */
    ResponseChain t1MerchantApply(Long uid, String pubKey, String merchantName);

    /**
     * 二级租赁商申请成功
     * @param belongTo
     * @param uid
     * @param pubKey
     * @param merchantName
     * @return
     */
    ResponseChain t2MerchantApply(Long belongTo, Long uid, String pubKey, String merchantName);

    /**
     * 融资购机
     * @param financeApplyDTO
     * @return
     */
    ResponseChain financeApply(EzFinanceApply financeApplyDTO);
}
