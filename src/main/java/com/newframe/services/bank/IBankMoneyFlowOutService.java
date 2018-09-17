package com.newframe.services.bank;

import com.newframe.entity.bank.BankMoneyFlow;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 银行流水 提现 服务类
 * </p>
 *
 * @author zww
 * @since 2018-07-13
 */
public interface IBankMoneyFlowOutService {


    /**
     * 提款审核通过
     *
     * @param bankMoneyFlowId
     * @return
     */
    @Transactional(rollbackFor = Exception.class, timeout = 20)
    BankMoneyFlow agreeAuditBankMoneyFlowOut(Long bankMoneyFlowId);

    /**
     * 出账成功
     *
     * @param bankMoneyFlow
     * @return status
     */
    @Transactional(rollbackFor = Exception.class, timeout = 20)
    void finishOutOk(BankMoneyFlow bankMoneyFlow);

    /**
     * 出账失败
     *
     * @param bankMoneyFlow
     * @return status
     */
    @Transactional(rollbackFor = Exception.class, timeout = 20)
    void finishOutNo(BankMoneyFlow bankMoneyFlow);

}
