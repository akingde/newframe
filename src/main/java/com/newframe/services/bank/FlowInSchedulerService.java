package com.newframe.services.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FlowInSchedulerService {
    @Autowired
    BankMoneyFlowInService bankMoneyFlowInService;

    @Scheduled(cron = "${schedule.moneyFlowInTask}")
    public void statusCheckTask() {
        BankSupport.syncRealTimeInSchedule();
    }

}