package com.newframe.services.bank.impl;

import com.newframe.entity.bank.BankMoneyFlow;
import com.newframe.enums.bank.BankMoneyFlowStatus;
import com.newframe.repositories.dataMaster.bank.BankMoneyFlowMaster;
import com.newframe.repositories.dataQuery.bank.BankMoneyFlowQuery;
import com.newframe.repositories.dataSlave.bank.BankMoneyFlowSlave;
import com.newframe.services.bank.BankMoneyFlowInService;
import com.newframe.services.bank.data.BankQueryType;
import com.newframe.services.bank.data.BankRealTimeResultBean;
import com.newframe.services.user.UserService;
import com.newframe.utils.MathUtils;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>
 * 银行流水 服务实现类
 * </p>
 *
 * @author zww
 * @since 2018-07-13
 */
@Service
public class BankMoneyFlowInServiceImpl implements BankMoneyFlowInService {
    @Autowired
    BankMoneyFlowSlave bankMoneyFlowSlave;
    @Autowired
    BankMoneyFlowMaster bankMoneyFlowMaster;
    @Autowired
    UserService userService;
    @Autowired
    IdGlobalGenerator idGlobalGenerator;

    private Logger logger = LoggerFactory.getLogger(getClass());
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    @Override
    public List<String> saveBankMoneyFlowIn(BankRealTimeResultBean bankFlowResultBean) {
        List<BankRealTimeResultBean.BankFlowDataBean> list = bankFlowResultBean.getData();
        if (null == list || list.isEmpty()) {
            return Collections.emptyList();
        }
        Set<String> bankTransactionNoSet = bankFlowResultBean.getTranNoSet();//银行流水(用于查询数据库)

        BankMoneyFlowQuery bankMoneyFlowQuery = new BankMoneyFlowQuery();
        bankMoneyFlowQuery.getBankTransactionNos().addAll(bankTransactionNoSet);
        //数据库存在的流水号
        List<BankMoneyFlow> existBankMoneyFlowList = bankMoneyFlowSlave.findAll(bankMoneyFlowQuery);
        Set<String> dbBankTraNoSet = new HashSet<>();
        for (BankMoneyFlow bankMoneyFlow : existBankMoneyFlowList) {
            dbBankTraNoSet.add(bankMoneyFlow.getBankTransactionNo());
        }

        List<BankMoneyFlow> listBankMoneyFlow = new ArrayList<>();
        for (BankRealTimeResultBean.BankFlowDataBean bankFlowDataBean : list) {
            String bankTransactionNo = bankFlowDataBean.getTran_FLOW();//银行交易流水
            if (dbBankTraNoSet.contains(bankTransactionNo)) {//银行交易流水已存在
                continue;
            }
            listBankMoneyFlow.add(convertInBankMoneyFlow(bankFlowDataBean));
        }

        if (!dbBankTraNoSet.isEmpty()) {
            logger.info("不再更新已存在的流水编号:{}", dbBankTraNoSet);

        }
        long time1 = System.currentTimeMillis();
        logger.info("保存银行最新充值流水到数据库----开始");
        int count = 0;
        List<String> resultList = new ArrayList<>();
        if (!listBankMoneyFlow.isEmpty()) {
            bankMoneyFlowMaster.saveAll(listBankMoneyFlow);
            //更新到业务系统
            for (BankMoneyFlow bankMoneyFlow : listBankMoneyFlow) {
                if (userService.addRechargeRecord(bankMoneyFlow.getBankCard(), bankMoneyFlow.getAmount()).getSucc()) {
                    resultList.add(bankMoneyFlow.getBankTransactionNo());
                } else {
                    logger.info("添加充值记录失败!bankNumber={},amount={}", bankMoneyFlow.getBankCard(), bankMoneyFlow.getAmount());
                    bankMoneyFlowMaster.deleteById(bankMoneyFlow.getId());
                }
            }
        }
        long time2 = System.currentTimeMillis();
        logger.info("保存银行最新充值流水到数据库----结束，耗时{}毫秒,完成{}条", time2 - time1, count);
        return resultList;
    }

    private BankMoneyFlow convertInBankMoneyFlow(BankRealTimeResultBean.BankFlowDataBean bankFlowDataBean) {
        BankMoneyFlow bankMoneyFlow = new BankMoneyFlow();//拿到建行所给数据
        bankMoneyFlow.setId(idGlobalGenerator.getSeqId(BankMoneyFlow.class));
        bankMoneyFlow.setBankCard(bankFlowDataBean.getAccno2());
        bankMoneyFlow.setAmount(BigDecimal.valueOf(Double.parseDouble(bankFlowDataBean.getAmt())));
        bankMoneyFlow.setBankTransactionNo(bankFlowDataBean.getTran_FLOW());
        bankMoneyFlow.setTransactionNo(MathUtils.getNo());
        bankMoneyFlow.setBankName(bankFlowDataBean.getCadbank_Nm());
        bankMoneyFlow.setType(BankQueryType.FLOW_IN);//充值
        bankMoneyFlow.setStatus(BankMoneyFlowStatus.IN_READY.getIntValue());
        bankMoneyFlow.setRemarks(bankFlowDataBean.getDet());
        try {
            bankMoneyFlow.setApplyTime(dateFormat.parse(bankFlowDataBean.getCreattime()).getTime());
        } catch (ParseException e) {
            logger.error("日期时间处理出错:{}", bankFlowDataBean.getCreattime());
            logger.error(e.getMessage());
        }
        return bankMoneyFlow;
    }


}
