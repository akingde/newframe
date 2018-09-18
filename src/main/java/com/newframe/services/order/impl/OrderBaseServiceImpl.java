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
    public String getRenterName(Long renterId){
        if(renterId == null){
            return null;
        }
        UserRentMerchant rentMerchant = rentMerchantService.findOne(renterId);
        if(rentMerchant != null){
            return rentMerchant.getMerchantName();
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


    @Override
    public void messagePush(Long receiverId, Long orderId, String associatedOrderId, MessagePushEnum info){
        pushService.sendMessToAllByUid(receiverId,info.getRoleId(), associatedOrderId,
                orderId, info.getTitle(), info.getType(), info.getContent());
        GwsLogger.getLogger().info("推送消息给："+receiverId+",消息内容："+info.toString());
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
        // todo 融资利息怎么算
        BigDecimal interest = BigDecimal.ZERO;
        BigDecimal monthPayment = orderFunder.getFinancingAmount()
                .divide(BigDecimal.valueOf(orderFunder.getNumberOfPeriods()),2,RoundingMode.HALF_UP);
        // 首付已经还清，所以待还和已还要除去首付
        accountManageService.saveAccountRenterFinancing(
                orderRenter.getRenterId(),
                orderRenter.getOrderId(),
                orderRenter.getPartnerOrderId(),
                orderFunder.getFinancingAmount(),
                orderFunder.getNumberOfPeriods(),
                orderFunder.getFinancingAmount(),
                interest,
                monthPayment,
                BigDecimal.ZERO,
                orderFunder.getFinancingAmount().subtract(monthPayment),
                interest);

        accountService.saveAccountFundingFinanceAssetDetail(orderFunder.getFunderId(),orderFunder.getOrderId(),Long.valueOf(orderFunder.getCtime()),
                orderRenter.getRenterId(),orderRenter.getRenterName(),orderRenter.getPartnerOrderId(),orderFunder.getFinancingAmount(),
                orderFunder.getNumberOfPeriods());
        return true;
    }
}
