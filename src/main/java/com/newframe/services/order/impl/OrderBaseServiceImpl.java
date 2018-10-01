package com.newframe.services.order.impl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.order.request.FinancingInfo;
import com.newframe.dto.order.response.SupplierInfoDTO;
import com.newframe.entity.order.OrderAssign;
import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderHirer;
import com.newframe.entity.order.OrderRenter;
import com.newframe.entity.user.UserRentMerchant;
import com.newframe.entity.user.UserSupplier;
import com.newframe.enums.order.MessagePushEnum;
import com.newframe.enums.order.OrderAssignStatusEnum;
import com.newframe.enums.order.OrderType;
import com.newframe.enums.order.PatternPaymentEnum;
import com.newframe.repositories.dataMaster.order.OrderAssignMaster;
import com.newframe.repositories.dataQuery.order.OrderAssignQuery;
import com.newframe.repositories.dataSlave.order.OrderFunderSlave;
import com.newframe.repositories.dataSlave.order.OrderHirerSlave;
import com.newframe.services.account.AccountManageService;
import com.newframe.services.account.AccountService;
import com.newframe.services.order.FormulaService;
import com.newframe.services.order.OrderBaseService;
import com.newframe.services.test.TestManageService;
import com.newframe.services.userbase.ConfigRateService;
import com.newframe.services.userbase.UserRentMerchantService;
import com.newframe.services.userbase.UserSupplierService;
import com.newframe.utils.cache.IdGlobalGenerator;
import com.newframe.utils.log.GwsLogger;
import org.apache.poi.ss.formula.functions.Finance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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
    private AccountService accountService;
    @Autowired
    private AccountManageService accountManageService;
    @Autowired
    private FormulaService formulaService;
    @Value("${residual.value.protection.scheme}")
    private BigDecimal residualValue;
    @Autowired
    private ConfigRateService configRateService;
    @Autowired
    private OrderAssignMaster orderAssignMaster;
    @Autowired
    private IdGlobalGenerator idGen;
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
        // 计算租机还款首付
        BigDecimal downPayment = orderHirer.getAccidentBenefit().add(orderHirer.getMonthlyPayment());
        if(PatternPaymentEnum.INSTALMENT_PAYMENT.getValue().equals(orderHirer.getPatternPayment())){
            paymentNumber = orderHirer.getNumberOfPeriods();
        }
        // 如果是全款支付，租期为1，首付为订单总金额+意外保险
        if(PatternPaymentEnum.FULL_PAYMENT.getValue().equals(orderHirer.getPatternPayment())){
            paymentNumber = 1;
            downPayment = orderHirer.getOrderAmount().add(orderHirer.getAccidentBenefit());
        }

        // 操作租赁商账户表和租赁商租赁账户明细
        OperationResult<Boolean> operationResult = accountManageService.saveAccountRenterRentDetail(
                orderRenter.getRenterId(),
                orderRenter.getOrderId(),
                orderRenter.getPartnerOrderId(),
                orderRenter.getProductBrand(),
                orderRenter.getProductName(),
                orderRenter.getProductColor(),
                String.valueOf(orderRenter.getProductStorage()),
                String.valueOf(orderRenter.getProductRandomMemory()),
                orderHirer.getOrderAmount(),
                paymentNumber,
                // 已付租金应该是首付 - 意外保险
                downPayment.subtract(orderHirer.getAccidentBenefit()),
                orderHirer.getOrderAmount().subtract(downPayment),
                orderHirer.getNumberOfPeriods()-1,
                orderRenter.getGatheringAccount(),
                orderHirer.getAccidentBenefit()
        );
        // 操作出租方账户表和生成租赁商还款明细
        accountService.saveAccountLessorMatterAssetDetail(
                orderHirer.getLessorId(),
                orderHirer.getOrderId(),
                Long.valueOf(orderRenter.getCtime()),
                orderRenter.getRenterId(),
                orderRenter.getRenterName(),
                orderRenter.getPartnerOrderId(),
                orderRenter.getProductBrand(),
                orderRenter.getProductName(),
                orderRenter.getProductColor(),
                String.valueOf(orderRenter.getProductStorage()),
                String.valueOf(orderRenter.getProductRandomMemory()),
                orderHirer.getOrderAmount(),
                orderHirer.getNumberOfPeriods(),orderHirer.getOrderAmount());
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
        BigDecimal monthlyDeposit = orderFunder.getDeposit().divide(BigDecimal.valueOf(orderFunder.getNumberOfPeriods()),2,RoundingMode.HALF_UP);
        // 融资信息封装
        FinancingInfo financingInfo = new FinancingInfo();
        financingInfo.setAveragePrincipal(orderFunder.getAveragePrincipal());
        financingInfo.setMonthPayment(orderFunder.getMonthlyPayment());
        financingInfo.setOnePrincipal(orderFunder.getOnePrincipal());
        financingInfo.setRate(configRateService.getRate());
        financingInfo.setSumAmount(orderFunder.getSumAmount());
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
                interest, new BigDecimal(0), monthlyDeposit,financingInfo);

        accountService.saveAccountFundingFinanceAssetDetail(
                orderFunder.getFunderId(),
                orderFunder.getOrderId(),
                Long.valueOf(orderFunder.getCtime()),
                orderRenter.getRenterId(),
                orderRenter.getRenterName(),
                orderRenter.getPartnerOrderId(),
                orderFunder.getFinancingAmount(),
                orderFunder.getNumberOfPeriods());
        return true;
    }

    @Override
    public BigDecimal getRentPrice(BigDecimal price,BigDecimal rate, Integer numberOfPayment){
        Double price1 = price.doubleValue();
        Double rate1 = rate.doubleValue();
        Double rentPrice = -Finance.pmt(rate1/12,numberOfPayment,price1);
        DecimalFormat format = new DecimalFormat("#.##");
        return BigDecimal.valueOf(Double.valueOf(format.format(rentPrice)));
    }

    @Override
    public void getSupplierInfo(SupplierInfoDTO supplierInfoDTO,BigDecimal supplyPrice,Integer periods,BigDecimal monthPayment){
        double rate = configRateService.getRate().doubleValue();
        supplierInfoDTO.setMonthPayment(monthPayment);
        supplierInfoDTO.setDownPayment(monthPayment);
        supplierInfoDTO.setAccidentBenefit(residualValue);
        supplierInfoDTO.setAveragePrincipal(formulaService.getAveragePrincipal(rate,periods,monthPayment));
        supplierInfoDTO.setOnePrincipal(formulaService.getOnePrincipal(supplierInfoDTO.getFinancingAmount(),supplierInfoDTO.getAveragePrincipal()));
        supplierInfoDTO.setSumAmount(formulaService.getSumAmount(supplierInfoDTO.getFinancingAmount(),supplierInfoDTO.getAveragePrincipal(),rate,periods));
    }

    @Override
    public void saveOrderAssign(Long orderId, Long renterId, Long examineId, OrderType orderType, OrderAssignStatusEnum orderAssignStatus){
        OrderAssign orderAssign = new OrderAssign();
        orderAssign.setId(idGen.getSeqId(OrderAssign.class));
        orderAssign.setExamineUid(examineId);
        orderAssign.setOrderId(orderId);
        orderAssign.setRentUid(renterId);
        orderAssign.setOrderType(orderType.getCode());
        orderAssign.setOrderStatus(orderAssignStatus.getCode());
        orderAssignMaster.save(orderAssign);
    }

    @Override
    public void updateOrderAssignStatus(Long orderId, Long renterId, Long examineId, OrderType orderType, OrderAssignStatusEnum orderAssignStatus){
        OrderAssign orderAssign = new OrderAssign();
        orderAssign.setOrderStatus(orderAssignStatus.getCode());
        OrderAssignQuery query = new OrderAssignQuery();
        query.setExamineUid(examineId);
        query.setOrderId(orderId);
        query.setRentUid(renterId);
        query.setOrderType(orderType.getCode());
        orderAssignMaster.update(orderAssign,query,OrderAssign.ORDER_STATUS);
    }
}
