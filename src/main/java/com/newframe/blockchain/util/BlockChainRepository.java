package com.newframe.blockchain.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.newframe.blockchain.constant.BlankChainUrlConst;
import com.newframe.blockchain.entity.*;
import com.google.protobuf.ByteString;
import com.newframe.services.http.OkHttpService;
import com.newframe.utils.SnowFlakeUtil;
import com.newframe.utils.log.GwsLogger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import types.EzTransfer;

import java.nio.charset.StandardCharsets;

/**
 * @author wangtao
 * @date 2018/6/15
 */
@Component
public class BlockChainRepository {

    @Value("${blockChain.url}")
    private String blockChainUrl;
    @Value("${blockChain.to}")
    private String blockChainTo;
    @Value("${blockChain.execer}")
    private String blockChainExecer;
    @Autowired
    private OkHttpService okHttpService;

    private static final String TX_NOT_EXIST = "tx not exist";

    /**
     * 上链
     */
    public ResponseChain sendTransaction(String publicKey, String privateKey, EzTransfer.EzAction actionBuilder) throws Exception{
        EzTransfer.Transaction.Builder transaction = EzTransfer.Transaction.newBuilder();

        transaction.setExecer(ByteString.copyFrom(blockChainExecer.getBytes(StandardCharsets.UTF_8)));
        transaction.setPayload(actionBuilder.toByteString());
        transaction.setFee(1000000);
        transaction.setNonce(SnowFlakeUtil.CHAIN.nextId());
        transaction.setTo(blockChainTo);
        ByteString sign = ED25519Util.sign(transaction.build().toByteArray(), HexUtil.hexToBytes(privateKey));

        EzTransfer.Signature.Builder signture = EzTransfer.Signature.newBuilder();
        signture.setTy(2);
        signture.setPubkey(ByteString.copyFrom(HexUtil.hexToBytes(publicKey)));
        signture.setSignature(sign);

        transaction.setSignature(signture);

        String transactionData = HexUtil.bytesToHex(transaction.build().toByteArray());
        DataBean bean = new DataBean();
        bean.setData(transactionData);
        RequestBean requestBean = new RequestBean<>(bean, BlankChainUrlConst.SEND_TRANSACTION);
        String requestBeanStr = JSON.toJSONString(requestBean);
        ResponseBean<String> responseBean = okHttpService.sendBlockChain(blockChainUrl, requestBeanStr);
        GwsLogger.getLogger().info("上链hash================="+responseBean);
        ResponseChain responseChain = new ResponseChain();
        if(responseBean == null){
            responseChain.setSuccess(false);
            responseChain.setError(null);
            return responseChain;
        }
        if(responseBean.getError() != null || responseBean.getResult() == null){
            responseChain.setSuccess(false);
            responseChain.setError(responseBean.getError());
            return responseChain;
        }
        ResponseBean<TransactionResultBean> queryTransaction = queryTransaction(responseBean.getResult());
        if(queryTransaction == null){
            responseChain.setSuccess(false);
            responseChain.setError(null);
            return responseChain;
        }
        if(StringUtils.isNotBlank(queryTransaction.getError())){
            responseChain.setSuccess(false);
            responseChain.setError(queryTransaction.getError());
            return responseChain;
        }
        responseChain.setSuccess(true);
        responseChain.setHash(responseBean.getResult());
        responseChain.setHeight(queryTransaction.getResult().getHeight());
        return responseChain;
    }

    /**
     * 根据哈希查询交易信息
     */
    private ResponseBean<TransactionResultBean> queryTransaction(String hash) throws Exception{
        for(int i=0; i<30; i++){
            Thread.sleep(500);
            DataBean bean = new DataBean();
            bean.setHash(hash);
            RequestBean requestBean = new RequestBean<>(bean, BlankChainUrlConst.QUERY_TRANSACTION);
            String requestBeanStr = JSON.toJSONString(requestBean);
            // 发送区块链
            ResponseBean<TransactionResultBean> queryTransaction = okHttpService.queryTransactionResult(blockChainUrl, requestBeanStr);
            if(queryTransaction == null){
                continue;
            }
            if(queryTransaction.getError() != null || queryTransaction.getResult() == null){
                if(TX_NOT_EXIST.equals(queryTransaction.getError())){
                    continue;
                }
            }
            return checkQueryTransaction(queryTransaction);
        }
        return null;
    }

    private ResponseBean<TransactionResultBean> checkQueryTransaction(ResponseBean<TransactionResultBean> queryTransaction){
        if(queryTransaction == null){
            return null;
        }
        if(queryTransaction.getError() != null || queryTransaction.getResult() == null){
            return queryTransaction;
        }

        if(queryTransaction.getResult().getReceipt().getTy() != 2){
            queryTransaction.setError(queryTransaction.getResult().getReceipt().getLogs().get(0).getLog());
            GwsLogger.getLogger().error("block chain error：======================>" + queryTransaction.getResult().getReceipt().getLogs().get(0).getLog());
            return queryTransaction;
        }
        return queryTransaction;
    }

}
