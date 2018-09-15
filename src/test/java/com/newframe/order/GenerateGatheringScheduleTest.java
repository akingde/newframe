package com.newframe.order;

import com.newframe.NewFrameApplicationTests;
import com.newframe.entity.order.OrderFunder;
import com.newframe.repositories.dataMaster.order.OrderFunderMaser;
import com.newframe.services.order.OrderBaseService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

/**
 * @author kfm
 * @date 2018.09.13 14:10
 */
public class GenerateGatheringScheduleTest extends NewFrameApplicationTests {
    @Autowired
    OrderBaseService orderBaseService;
    @Autowired
    OrderFunderMaser orderFunderMaser;

    @Test
    public void generateFundingSchedule() throws Exception{
        List<OrderFunder> orderFunders = orderFunderMaser.findAll();
        for(OrderFunder orderFunder:orderFunders){
            orderBaseService.generateFundingSchedule(orderFunder.getFinancingAmount(),orderFunder.getNumberOfPeriods(),
                    orderFunder.getMerchantId(),orderFunder.getFunderId(),orderFunder.getOrderId());
        }
    }
    @Test
    public void testMvc(){
//        mockMvc.perform(post(""))
    }
}
