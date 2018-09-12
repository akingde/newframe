package com.newframe.services.order.impl;

import com.newframe.controllers.api.TestCommonController;
import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderHirer;
import com.newframe.entity.order.OrderRenter;
import com.newframe.entity.user.UserRentMerchant;
import com.newframe.entity.user.UserSupplier;
import com.newframe.enums.order.MessagePushEnum;
import com.newframe.repositories.dataSlave.order.OrderFunderSlave;
import com.newframe.repositories.dataSlave.order.OrderHirerSlave;
import com.newframe.services.order.OrderBaseService;
import com.newframe.services.test.TestManageService;
import com.newframe.services.userbase.UserRentMerchantService;
import com.newframe.services.userbase.UserSupplierService;
import com.newframe.utils.log.GwsLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author kfm
 * @date 2018.09.03 20:33
 */
@Service
public class OrderBaseServiceImpl implements OrderBaseService {

    @Autowired
    private UserSupplierService supplierService;

    @Autowired
    private UserRentMerchantService rentMerchantService;

    @Autowired
    private OrderFunderSlave orderFunderSlave;

    @Autowired
    private OrderHirerSlave orderHirerSlave;

    @Autowired
    private TestManageService pushService;
    @Override
    public String getSupplierName(Long supplierId){
        if(supplierId == null){
            return null;
        }
        UserSupplier supplier = supplierService.findOne(supplierId);
        if(supplier != null){
            return supplier.getMerchantName();
        }
        return null;
    }

    @Override
    public String getRenterPhone(Long renterId){
        if(renterId == null){
            return null;
        }
        UserRentMerchant rentMerchant = rentMerchantService.findOne(renterId);
        if(rentMerchant != null){
            return rentMerchant.getMerchantPhoneNumber();
        }
        return null;
    }

    @Override
    public Integer getOrderFinancingTimes(Long orderId) {
        if(orderId == null){
            return 0;
        }
        OrderFunder orderFunder = orderFunderSlave.findOne(orderId);
        if(orderFunder != null){
            return orderFunder.getDispatchTimes();
        }
        return 0;
    }

    @Override
    public Integer getOrderRentTimes(Long orderId) {
        if(orderId == null){
            return 0;
        }
        OrderHirer orderHirer = orderHirerSlave.findOne(orderId);
        if(orderHirer != null){
            return orderHirer.getDispatchTimes();
        }
        return 0;
    }

    /**
     * 生成租赁商还款计划
     * @param financingAmount 融资金额
     * @param tenancyTerm 融资期限
     * @param renterId 租赁商id
     * @param funderId 资金方id
     * @param orderId 订单id
     */
    public void generateFundingSchedule(BigDecimal financingAmount,Integer tenancyTerm,
                                        Long renterId,Long funderId,Long orderId) throws ParseException {
        // 计算月租金
        BigDecimal monthlyAmount = financingAmount.divide(
                new BigDecimal(tenancyTerm),2,RoundingMode.HALF_UP
        );
        Calendar calendar = Calendar.getInstance();
        List<Integer> schedule = getFundingScheduleTime(calendar,tenancyTerm);

    }

    @Override
    public void messagePush(Long receiverId, Long orderId, String associatedOrderId, MessagePushEnum info){
        pushService.sendMessToAllByUid(receiverId,info.getRoleId(), associatedOrderId,
                orderId, info.getTitle(), info.getType(), info.getContent());
        GwsLogger.getLogger().info("推送消息给："+receiverId+",消息内容："+info.toString());
    }

    /**
     * 生成租赁商向资金方还款的还款计划时间表，根据当前日期往后推一天生成
     *
     * @param calendar 放款时间
     * @param tenancyTerm 还款期限
     * @return 时间表
     */
    private List<Integer> getFundingScheduleTime(Calendar calendar, Integer tenancyTerm) throws ParseException {
        calendar.setTimeInMillis(calendar.getTimeInMillis() + 24*60*60*1000);
        Integer currentMonth = calendar.get(Calendar.MONTH) + 1;
        Integer currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        Integer currentYear = calendar.get(Calendar.YEAR);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        List<Integer> schedule = new ArrayList<>();
        // 当前日期的下一天为第一次还款日期
        schedule.add(Long.valueOf(format.parse(currentYear.toString()+
                (currentMonth<10?"0"+currentMonth:currentMonth) +
                (currentDay<10?"0"+currentDay:currentDay)
            ).getTime()/1000).intValue());
        for(int i=0;i<tenancyTerm-1;i++){
            if(currentMonth.equals(12)){
                currentMonth = 1;
                currentYear++;
            }else{
                currentMonth++;
            }
            Date date = format.parse(currentYear.toString()+
                    (currentMonth<10?"0"+currentMonth:currentMonth) +
                    (currentDay<10?"0"+currentDay:currentDay)
            );
            schedule.add(Long.valueOf(date.getTime()/1000).intValue());
        }
        return schedule;
    }
}
