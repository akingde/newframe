package com.newframe.blockchain.impl;

import com.newframe.blockchain.BlockChain;
import com.newframe.blockchain.entity.ResponseBean;
import com.newframe.blockchain.entity.ResponseChain;
import com.newframe.blockchain.util.BlockChainRepository;
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

    @Override
    public ResponseChain funderCheckPass(Long uid, String merchantName){

        EzTransfer.EzFunderApply.Builder builder = EzTransfer.EzFunderApply.newBuilder();
        builder.setUid(uid);
        builder.setMerchantName(merchantName);
        EzTransfer.EzAction.Builder action = EzTransfer.EzAction.newBuilder();
        action.setFunderApply(builder);
        action.setTy(EzTransfer.EzActionType.AT_FUNDER_APPLY);
        try {
            return blockChainRepository.sendTransaction(adminPubkey, adminPriKey, action.build());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
