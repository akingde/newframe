package com.newframe.merchant;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.newframe.NewFrameApplicationTests;
import com.newframe.dto.merchant.order.MerchantOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.14 14:29
 */
@Slf4j
public class MerchantOrderControllerTest extends NewFrameApplicationTests {
    @Test
    public void merchantOrderTest() throws Exception {
        MerchantOrderDTO merchantOrderDTO = new MerchantOrderDTO();
        merchantOrderDTO.setAccidentBenefit(new BigDecimal(199));
        merchantOrderDTO.setDownPayment(new BigDecimal(598));
        merchantOrderDTO.setMonthlyPayment(new BigDecimal(399));
        merchantOrderDTO.setNumberOfPayments(12);
        merchantOrderDTO.setPartnerId(1535433927622895L);
        merchantOrderDTO.setPartnerOrderId("52_1234567");
        merchantOrderDTO.setProductBrand("apple");
        merchantOrderDTO.setProductName("iPhone X");
        merchantOrderDTO.setProductColor("黑色");
        merchantOrderDTO.setProductStorage(128);
        merchantOrderDTO.setProductRandomMemory(4);
        merchantOrderDTO.setRenterIdNumber("330781199605282025");
        merchantOrderDTO.setUserAddress("浙江省杭州市西湖区马腾路");
        merchantOrderDTO.setUserCreditLine(1);
        merchantOrderDTO.setUserCreditScore(789);
        merchantOrderDTO.setUserIdNumber("622301199604141738");
        merchantOrderDTO.setUserMobile("15957180382");
        merchantOrderDTO.setUserRealname("李小末");
        log.info(new Gson().toJson(merchantOrderDTO));
        ResultActions actions = this.mockMvc.perform(post("/rent/api/merchant/order/pushOrder")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new Gson().toJson(merchantOrderDTO))
                );
        actions.andExpect(status().isOk()).andDo(print());
        log.info(actions.andReturn().getResponse().getContentAsString());
        JSONObject result = (JSONObject) JSONObject.parse(actions.andReturn().getResponse().getContentAsString());
        Assert.assertTrue(result.getBoolean("data"));
    }
}
