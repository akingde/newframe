package com.newframe.services.bank;

import com.newframe.services.bank.data.*;
import com.newframe.utils.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 银行restful工具类
 * <p>
 * Created by tt on 7/13/18.
 */
public class BankRestUtils {
    private static Logger logger = LoggerFactory.getLogger(BankRestUtils.class);

    /**
     * 查询最新充值流水
     *
     * @param url
     * @param account
     * @return
     */
    public static BankRealTimeResultBean realTimeInQuery(String url, String account) {
        logger.debug("请求:{},参数:{}", url, account);
        BankRealTimeQueryBean queryBankFlowBean = BankRealTimeQueryBean.builder()
                .accountid(account)
                .flag(BankQueryType.FLOW_IN)//0:提现,1:充值
                .build();
        ResponseEntity<BankRealTimeResultBean> responseEntity = RestUtils.getRestTemplate().postForEntity(url, queryBankFlowBean, BankRealTimeResultBean.class);
        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            logger.debug("获取最新银行流水:{}", responseEntity.getBody());
            return responseEntity.getBody();
        } else {
            logger.error("请求银行接口失败statusCode={}", responseEntity.getStatusCode());
        }
        return new BankRealTimeResultBean();
    }


    /**
     * 确认银行流水号，下次不再查询出结果
     *
     * @param url
     * @param account
     * @param nos     银行流水号
     */
    public static boolean realTimeQueryConfirm(String url, String account, List<String> nos) {
        logger.debug("请求:{}", url);
        logger.debug("帐号:{},银行流水号:{}", account, nos);
        BankRealTimeQueryBean queryBankFlowBean = BankRealTimeQueryBean.builder()
                .accountid(account)
                .flag(BankQueryType.CONFIRM)
                .tranflows(nos)
                .build();
        //设置流水状态，下次不再查出
        ResponseEntity<BankRealTimeResultBean> responseEntity = RestUtils.getRestTemplate().postForEntity(url, queryBankFlowBean, BankRealTimeResultBean.class);
        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            logger.debug("确认完成银行流水号={}", nos);
            return true;
        }
        logger.error("请求银行接口失败statusCode={}", responseEntity.getStatusCode());
        return false;
    }


    /**
     * 查询交易结果
     *
     * @param url
     * @param bankDealQueryBean 业务序列号,银行卡号
     * @return 交易结果码
     */
    public static String bankDealQuery(String url, BankDealQueryBean bankDealQueryBean) {
        logger.debug("请求:{},参数:{}", url, bankDealQueryBean);
        ResponseEntity<BankDealResultBean> responseEntity = RestUtils.getRestTemplate().postForEntity(url, bankDealQueryBean, BankDealResultBean.class);
        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            String code = responseEntity.getBody().getDealresult();
            return code;
        }
        logger.error("请求银行接口失败statusCode={}", responseEntity.getStatusCode());
        throw new RuntimeException(":bankDealQuery");
    }


    /**
     * 转帐
     *
     * @param url
     * @param bankDealTransferBean 业务序列号
     * @return serialNumber 银行交易号
     */
    public static String bankDealTransfer(String url, BankDealTransferBean bankDealTransferBean) {
        logger.debug("请求:{},参数:{}", url, bankDealTransferBean);
        ResponseEntity<BankDealResultBean> responseEntity = RestUtils.getRestTemplate().postForEntity(url, bankDealTransferBean, BankDealResultBean.class);
        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            return responseEntity.getBody().getSerialnumber();
        }
        logger.error("请求银行接口失败statusCode={}", responseEntity.getStatusCode());
        throw new RuntimeException(":bankDealTransfer");
    }
}
