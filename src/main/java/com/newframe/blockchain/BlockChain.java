package com.newframe.blockchain;

import com.newframe.blockchain.entity.ResponseChain;
import org.springframework.stereotype.Service;

/**
 * @author WangBin
 */
public interface BlockChain {

    ResponseChain funderCheckPass(Long uid, String merchantName);
}
