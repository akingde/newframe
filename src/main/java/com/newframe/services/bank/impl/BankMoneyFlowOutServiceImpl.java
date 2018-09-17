package com.newframe.services.bank.impl;

import com.newframe.entity.bank.BankMoneyFlow;
import com.newframe.enums.bank.BankMoneyFlowStatus;
import com.newframe.repositories.dataMaster.bank.BankMoneyFlowMaster;
import com.newframe.repositories.dataSlave.bank.BankMoneyFlowSlave;
import com.newframe.services.bank.BankMoneyFlowOutService;
import com.newframe.services.bank.BankSupport;
import com.newframe.services.user.UserService;
import com.newframe.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 银行流水 服务实现类
 * </p>
 *
 * @author zww
 * @since 2018-07-13
 */
@Service
public class BankMoneyFlowOutServiceImpl implements BankMoneyFlowOutService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    @Autowired
    BankMoneyFlowSlave bankMoneyFlowSlave;
    @Autowired
    BankMoneyFlowMaster bankMoneyFlowMaster;

    @Autowired
    UserService userService;

    @Override
    public BankMoneyFlow agreeAuditBankMoneyFlowOut(Long bankMoneyFlowId) {
        BankMoneyFlow bankMoneyFlow = bankMoneyFlowSlave.findOne(bankMoneyFlowId);
        if (null == bankMoneyFlow) {
            return null;
        }
        //提交银行处理
        String serialNumber = BankSupport.dealTransfer(bankMoneyFlow);
        if (StringUtils.isEmpty(serialNumber)) {
            logger.error("银行处理失败:返回serialNumber为空");
        }

        //更新银行返回序列号
        BankMoneyFlow moneyFlow = new BankMoneyFlow();
        moneyFlow.setId(bankMoneyFlow.getId());
        moneyFlow.setSerialNumber(serialNumber);
        bankMoneyFlowMaster.updateById(moneyFlow, moneyFlow.getId(), "serialNumber");

        //新线程处理银行结果
        try {
            executorService.schedule(() -> {
                bankMoneyFlow.setVersion(bankMoneyFlow.getVersion() + 1);
                Boolean bool = BankSupport.dealTransferQuery(bankMoneyFlow);
                if (null != bool) {
                    if (bool) {
                        finishOutOk(bankMoneyFlow);
                    } else {
                        finishOutNo(bankMoneyFlow);
                    }
                }
            }, 3, TimeUnit.SECONDS);

        } catch (Exception e) {
            logger.error("新线程提现处理失败:" + e.getMessage());
        }

        return bankMoneyFlow;
    }


    @Override
    public void finishOutOk(BankMoneyFlow bankMoneyFlow) {
        bankMoneyFlow.setStatus(BankMoneyFlowStatus.OUT_AUDIT_BANK_SUCCESS.getIntValue());
        bankMoneyFlow.setFinishTime(DateUtils.getTimeOfEastEight());
        bankMoneyFlowMaster.updateById(bankMoneyFlow, bankMoneyFlow.getId(),
                "version", "finishTime", "status");
        userService.bankDrawByPass(bankMoneyFlow.getId());
    }

    @Override
    public void finishOutNo(BankMoneyFlow bankMoneyFlow) {
        bankMoneyFlow.setStatus(BankMoneyFlowStatus.OUT_AUDIT_BANK_FAIL.getIntValue());
        bankMoneyFlow.setFinishTime(DateUtils.getTimeOfEastEight());
        bankMoneyFlowMaster.updateById(bankMoneyFlow, bankMoneyFlow.getId(),
                "version", "finishTime", "status");
        userService.bankDrawByFail(bankMoneyFlow.getId());
    }


}
