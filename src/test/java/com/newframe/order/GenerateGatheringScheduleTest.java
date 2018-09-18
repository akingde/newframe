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
import java.util.Random;

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
    public void testMvc(){
//        mockMvc.perform(post(""))
    }

    @Test
    public void test(){
        Random random = new Random(2);
        System.out.println(random.nextInt());
    }
}
