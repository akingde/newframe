package com.newframe.services.bank;

import com.newframe.entity.bank.BankMoneyFlow;
import com.newframe.enums.bank.BankMoneyFlowStatus;
import com.newframe.repositories.dataMaster.bank.BankMoneyFlowMaster;
import com.newframe.repositories.dataQuery.bank.BankMoneyFlowQuery;
import com.newframe.repositories.dataSlave.bank.BankMoneyFlowSlave;
import com.newframe.services.user.UserService;
import com.newframe.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class FlowOutSchedulerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 正在处理提现
     */
    private static AtomicBoolean isFlowOutDoing = new AtomicBoolean(false);

    @Autowired
    BankMoneyFlowSlave bankMoneyFlowSlave;
    @Autowired
    BankMoneyFlowMaster bankMoneyFlowMaster;

    @Autowired
    UserService userService;

    @Scheduled(cron = "${schedule.moneyFlowOutTask}")
    public void statusCheckTask() {
        if (isFlowOutDoing.compareAndSet(false, true)) {
            return;
        }
        logger.info("执行提现定时任务（完成银行出帐）查询.....");
        try {
            BankMoneyFlowQuery bankMoneyFlowQuery = new BankMoneyFlowQuery();
            bankMoneyFlowQuery.setStatus(BankMoneyFlowStatus.OUT_AUDIT_SUCCESS.getIntValue());
            List<BankMoneyFlow> list = bankMoneyFlowSlave.findAll(bankMoneyFlowQuery);
            long time1 = System.currentTimeMillis();
            logger.info("执行定时任务（完成银行出帐）开始.....共{}条需要处理", list.size());
            int count = 0;
            for (BankMoneyFlow bankMoneyFlow : list) {
                Boolean bool = BankSupport.dealTransferQuery(bankMoneyFlow);
                if (null != bool) {
                    count++;
                    bankMoneyFlow.setStatus(bool ? BankMoneyFlowStatus.OUT_BANK_SUCCESS.getIntValue() : BankMoneyFlowStatus.OUT_BANK_FAIL.getIntValue());
                    bankMoneyFlow.setFinishTime(DateUtils.getTimeOfEastEight());
                    bankMoneyFlowMaster.updateById(bankMoneyFlow, bankMoneyFlow.getId(), "status", "finishTime");
                    if (bool) {
                        userService.bankDrawByPass(bankMoneyFlow.getId());
                    } else {
                        userService.bankDrawByFail(bankMoneyFlow.getId());
                    }
                }
            }
            long time2 = System.currentTimeMillis();
            logger.info("执行提现定时任务（完成银行出帐）结束.....完成{}条,耗时{}毫秒", count, time2 - time1);

        } catch (Exception e) {
            logger.error("执行提现定时任务（完成银行出帐）出错:{}", e.getMessage());
        } finally {
            isFlowOutDoing.set(false);
        }
    }

}