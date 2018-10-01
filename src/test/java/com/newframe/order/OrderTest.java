package com.newframe.order;

import com.newframe.NewFrameApplicationTests;
import com.newframe.enums.order.OrderAssignStatusEnum;
import com.newframe.enums.order.OrderType;
import com.newframe.services.order.OrderBaseService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * @author kfm
 * @date 2018.09.18 14:51
 */
public class OrderTest extends NewFrameApplicationTests {

    @Autowired
    OrderBaseService orderBaseService;

    @org.junit.Test
    public void test(){
        Random random = new Random();
        for (int i=0;i<100;i++)
        System.out.println(random.nextInt(3));
    }

    @org.junit.Test
    public void saveOrderAssignStatus(){
        orderBaseService.saveOrderAssign(1536923072812696L,1535433927623099L,1535433927623092L,OrderType.FUNDER_ORDER,OrderAssignStatusEnum.AUDIT);
    }

    @Test
    public void updateOrderAssignStatus(){
        orderBaseService.updateOrderAssignStatus(1536923072812696L,1535433927623099L,1535433927623092L,OrderType.FUNDER_ORDER,OrderAssignStatusEnum.PASS);
    }
}
