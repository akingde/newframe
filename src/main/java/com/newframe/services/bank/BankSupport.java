package com.newframe.services.bank;

import com.newframe.entity.bank.BankMoneyFlow;
import com.newframe.services.bank.data.BankDealQueryBean;
import com.newframe.services.bank.data.BankDealResultBean;
import com.newframe.services.bank.data.BankDealTransferBean;
import com.newframe.services.bank.data.BankRealTimeResultBean;
import com.newframe.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 银行资金处理辅助类
 * <p>
 * Created by tt on 7/13/18.
 */
public class BankSupport {
    /**
     * 正在同步银行流水
     */
    private static AtomicBoolean isFlowInDoing = new AtomicBoolean(false);

    private static Logger logger = LoggerFactory.getLogger(BankSupport.class);

    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * 处理银行返回结果(充值记录)
     * 插入数据库
     *
     * @param bankFlowResultBean
     */
    private static List<String> saveBankMoneyFlowIn(BankRealTimeResultBean bankFlowResultBean) {
        BankMoneyFlowInService bankMoneyFlowService = SpringContextHolder.getBean(BankMoneyFlowInService.class);
        List<String> resultList = bankMoneyFlowService.saveBankMoneyFlowIn(bankFlowResultBean);
        return resultList;
    }

    /**
     * 同步银行充值流水到数据库
     *
     * @return
     */
    private static boolean syncRealTimeIn() {
        logger.debug("查询银行最新的充值流水...");
        Environment env = SpringContextHolder.getBean(Environment.class);
        String account = env.getProperty("bank.account");
        String queryUrl = env.getProperty("bank.realtime.query");
        String confirmUrl = env.getProperty("bank.realtime.confirm");
        //查询实时流水
        BankRealTimeResultBean bankFlowResultBean = BankRestUtils.realTimeInQuery(queryUrl, account);
        List<String> nos = bankFlowResultBean.getTranNoList();
        logger.info("发现{}条最新的充值流水", nos.size());
        if (nos.isEmpty()) {
            return true;
        }
        //保存到数据库
        logger.debug("保存银行最新的充值流水到数据库...");
        saveBankMoneyFlowIn(bankFlowResultBean);
        //确认完成流水处理
        logger.debug("向银行确认完成最新的充值流水...");
        boolean result = BankRestUtils.realTimeQueryConfirm(confirmUrl, account, nos);
        return result;
    }


    /**
     * 新线程执行同步银行充值流水到数据库
     *
     * @return
     */
    public static boolean syncRealTimeInSchedule() {
        if (!isFlowInDoing.compareAndSet(false, true)) {
            return false;
        }
        executorService.schedule(() -> {
            long time1 = System.currentTimeMillis();
            logger.info("银行数据同步开始....");
            try {
                syncRealTimeIn();
            } catch (Exception e) {
                logger.error("银行数据同步出错:{}", e.getMessage());
            } finally {
                isFlowInDoing.set(false);
                long time2 = System.currentTimeMillis();
                logger.info("银行数据同步结束....耗时{}毫秒", time2 - time1);
            }
        }, 0, TimeUnit.SECONDS);
        return true;
    }


    /**
     * 同意提现
     *
     * @return
     */
    public static String dealTransfer(BankMoneyFlow bankMoneyFlow) {
        logger.debug("向银行申请提现:{}", bankMoneyFlow);
        Environment env = SpringContextHolder.getBean(Environment.class);
        String transferUrl = env.getProperty("bank.deal.transfer");
        BankDealTransferBean transferBean = BankDealTransferBean.builder()
                .accno(bankMoneyFlow.getBankCard())
                .accnoname(bankMoneyFlow.getBankName())
                .amount(bankMoneyFlow.getAmount().doubleValue())
                .pecvopenaccdept(bankMoneyFlow.getSubBankName())
                .pevvaccname(bankMoneyFlow.getBankCardHolder())
                .useof("提现")
                .build();
        String serialNumber = BankRestUtils.bankDealTransfer(transferUrl, transferBean);
        return serialNumber;
    }


    /**
     * 查看同意提现是否完成
     *
     * @return
     */
    public static Boolean dealTransferQuery(BankMoneyFlow bankMoneyFlow) {
        logger.debug("查询银行出账情况:{}", bankMoneyFlow);
        Environment env = SpringContextHolder.getBean(Environment.class);
        String queryUrl = env.getProperty("bank.deal.query");
        BankDealQueryBean queryBean = BankDealQueryBean.builder()
                .serialnumber(bankMoneyFlow.getSerialNumber())
                .bankCard(bankMoneyFlow.getBankCard())
                .build();
        String code = BankRestUtils.bankDealQuery(queryUrl, queryBean);
        if (BankDealResultBean.okSet.contains(code)) {
            logger.info("银行出账成功:{}", queryBean);
            return true;
        }
        if (BankDealResultBean.noSet.contains(code)) {
            logger.info("银行出账失败:{}", queryBean);
            return false;
        }
        logger.info("银行出账中:{}", queryBean);
        return null;
    }

}
