package com.newframe.services.task;

import com.newframe.entity.order.OrderAssign;
import com.newframe.repositories.dataQuery.order.OrderAssignQuery;
import com.newframe.repositories.dataSlave.order.OrderAssignSlave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author WangBin
 */
@Component
public class OrderTask {

    @Autowired
    private OrderAssignSlave orderAssignSlave;
    @Autowired
    private AsyncTimeoutOrder asyncTimeoutOrder;

    private static final int smallSendMessageTime = 20 * 60;
    private static final int bigSendMessageTime = 23 * 60;
    private static final int timeoutTime = 30 * 60;

    @Scheduled(fixedDelay = 180 * 1000L)
    public void timeoutOrder(){

        OrderAssignQuery query = new OrderAssignQuery();
        query.setOrderStatus(0);

        List<OrderAssign> assigns = orderAssignSlave.findAll(query);

        List<OrderAssign> msgList = assigns.parallelStream()
                .filter(item -> item.getCtime().intValue() < bigSendMessageTime)
                .filter(item -> item.getCtime().intValue() >= smallSendMessageTime)
                .collect(toList());
        List<OrderAssign> timeoutList = assigns.parallelStream()
                .filter(item -> item.getCtime().intValue() >= timeoutTime)
                .collect(toList());
        asyncTimeoutOrder.sendMsg(msgList);
        asyncTimeoutOrder.timeout(timeoutList);
    }
}
