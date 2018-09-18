package com.newframe.services.bank;

import com.newframe.services.bank.data.BankRealTimeResultBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 银行流水 充值 服务类
 * </p>
 *
 * @author zww
 * @since 2018-07-13
 */
public interface BankMoneyFlowInService {

    /**
     * 保存充值（从银行流水抽取过来数据）
     *
     * @param bankFlowResultBean
     * @return
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    List<String> saveBankMoneyFlowIn(BankRealTimeResultBean bankFlowResultBean);
}
