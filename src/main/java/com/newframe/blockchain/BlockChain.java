package com.newframe.blockchain;

import com.newframe.blockchain.entity.ResponseBean;
import com.newframe.blockchain.entity.ResponseChain;
import com.newframe.blockchain.entity.TransactionResultBean;
import org.springframework.stereotype.Service;

/**
 * @author WangBin
 */
public interface BlockChain {

    /**
     * 资金方申请上链
     * @param uid
     * @param merchantName
     * @return
     */
    ResponseChain funderCheckPass(Long uid, String merchantName);
}
