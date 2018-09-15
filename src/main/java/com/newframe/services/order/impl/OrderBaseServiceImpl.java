package com.newframe.services.order.impl;

import com.newframe.controllers.api.TestCommonController;
import com.newframe.dto.OperationResult;
import com.newframe.entity.order.FundingGatheringSchedule;
import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderHirer;
import com.newframe.entity.order.OrderRenter;
import com.newframe.entity.user.UserRentMerchant;
import com.newframe.entity.user.UserSupplier;
import com.newframe.enums.order.MessagePushEnum;
import com.newframe.enums.order.PatternPaymentEnum;
import com.newframe.repositories.dataMaster.order.FundingGatheringScheduleMaster;
import com.newframe.repositories.dataSlave.order.FundingGatheringScheduleSlave;
import com.newframe.repositories.dataSlave.order.OrderFunderSlave;
import com.newframe.repositories.dataSlave.order.OrderHirerSlave;
import com.newframe.services.account.AccountManageService;
import com.newframe.services.account.AccountService;
import com.newframe.services.order.OrderBaseService;
import com.newframe.services.test.TestManageService;
import com.newframe.services.userbase.UserRentMerchantService;
import com.newframe.services.userbase.UserSupplierService;
import com.newframe.utils.cache.IdGlobalGenerator;
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
    @Autowired
    private IdGlobalGenerator idGen;
    @Autowired
    private FundingGatheringScheduleMaster fundingGatheringScheduleMaster;
    @Autowired
    private FundingGatheringScheduleSlave fundingGatheringScheduleSlave;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountManageService accountManageService;
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

    public void generateRenterSchedule(Long renterId,Long payeeId,Integer orderType,Integer orderAmount ){

    }

    /**
     * 生成资金方收款计划
     * @param financingAmount 融资金额
     * @param tenancyTerm 融资期限
     * @param renterId 租赁商id
     * @param funderId 资金方id
     * @param orderId 订单id
     */
    @Override
    public void generateFundingSchedule(BigDecimal financingAmount,Integer tenancyTerm,
                                        Long renterId,Long funderId,Long orderId) throws ParseException {
        // 计算月租金
        BigDecimal monthlyAmount = financingAmount.divide(
                new BigDecimal(tenancyTerm),2,RoundingMode.HALF_UP
        );
        Calendar calendar = Calendar.getInstance();
        List<Integer> schedule = getFundingScheduleTime(calendar,tenancyTerm);
        // 日利率
        BigDecimal rate = new BigDecimal("0.0006");
        List<FundingGatheringSchedule> gatheringSchedules = new ArrayList<>();
        for(int i=0;i<tenancyTerm;i++){
            FundingGatheringSchedule fundingGatheringSchedule = new FundingGatheringSchedule();
            fundingGatheringSchedule.setId(idGen.getSeqId(FundingGatheringSchedule.class));
            fundingGatheringSchedule.setUid(funderId);
            fundingGatheringSchedule.setOrderId(orderId);
            fundingGatheringSchedule.setRenterId(renterId);
            fundingGatheringSchedule.setRepayAmount(monthlyAmount);
            // todo 利息暂时按照日利息0.0006计算，利息=剩余待还金额*日利率*30
            BigDecimal interest = financingAmount.subtract(
                    monthlyAmount.multiply(new BigDecimal(i))
            ).multiply(rate).multiply(new BigDecimal(30));
            fundingGatheringSchedule.setInterest(interest);
            fundingGatheringSchedule.setTotalAmount(monthlyAmount.add(interest));
            fundingGatheringSchedule.setLastRepayTime(schedule.get(i));
            fundingGatheringSchedule.setRepayStatus(1);
            fundingGatheringSchedule.setNumberPeriods(i+1);
            gatheringSchedules.add(fundingGatheringSchedule);
        }
        fundingGatheringScheduleMaster.saveAll(gatheringSchedules);
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

    @Override
    public boolean renterRentAccountOperation(OrderRenter orderRenter, OrderHirer orderHirer){
        // 还款期限，如果是分期付款，则总租金/租期=月租金
        // 如果是全款付款，还款期限为1
        Integer paymentNumber = 1;
        if(PatternPaymentEnum.INSTALMENT_PAYMENT.getValue().equals(orderHirer.getPatternPayment())){
            paymentNumber = orderHirer.getNumberOfPeriods();
        }
        if(PatternPaymentEnum.INSTALMENT_PAYMENT.getValue().equals(orderHirer.getPatternPayment())){
            paymentNumber = 1;
        }
        // 操作租赁商账户表和租赁商租赁账户明细
        OperationResult<Boolean> operationResult = accountManageService.saveAccountRenterRentDetail(
                orderRenter.getRenterId(),orderRenter.getOrderId(),orderRenter.getPartnerOrderId(),orderRenter.getProductBrand(),
                orderRenter.getProductName(),orderRenter.getProductColor(),String.valueOf(orderRenter.getProductStorage()),
                String.valueOf(orderRenter.getProductRandomMemory()),orderHirer.getOrderAmount(),paymentNumber,
                new BigDecimal("0"),orderHirer.getOrderAmount()
        );
        // 操作出租方账户表和生成租赁商还款明细
        accountService.saveAccountLessorMatterAssetDetail(orderHirer.getLessorId(),orderHirer.getOrderId(),Long.valueOf(orderRenter.getCtime()),
                orderRenter.getRenterId(),orderRenter.getRenterName(),orderRenter.getPartnerOrderId(),orderRenter.getProductBrand(),
                orderRenter.getProductName(),orderRenter.getProductColor(),String.valueOf(orderRenter.getProductStorage()),
                String.valueOf(orderRenter.getProductRandomMemory()),orderHirer.getOrderAmount(),orderHirer.getNumberOfPeriods());
        if(operationResult != null){
            return operationResult.getEntity();
        }
        return false;
    }

    @Override
    public boolean renterFunderAccountOperation(OrderRenter orderRenter, OrderFunder orderFunder) {
        accountService.saveAccountFundingFinanceAssetDetail(orderFunder.getUid(),orderFunder.getOrderId(),Long.valueOf(orderFunder.getCtime()),
                orderRenter.getRenterId(),orderRenter.getRenterName(),orderRenter.getPartnerOrderId(),orderFunder.getFinancingAmount(),
                orderFunder.getNumberOfPeriods());
        return false;
    }
}
