package com.newframe.services.task;

import com.newframe.controllers.JsonResult;
import com.newframe.dto.order.request.FinanceApplyDTO;
import com.newframe.dto.order.request.ProductInfoDTO;
import com.newframe.dto.order.response.LessorInfoDTO;
import com.newframe.dto.user.response.ProductDTO;
import com.newframe.entity.order.OrderAssign;
import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderHirer;
import com.newframe.entity.order.OrderRenter;
import com.newframe.entity.user.ProductLessor;
import com.newframe.entity.user.UserBaseInfo;
import com.newframe.entity.user.UserFunder;
import com.newframe.entity.user.UserHirer;
import com.newframe.enums.order.OrderType;
import com.newframe.repositories.dataQuery.order.OrderAssignQuery;
import com.newframe.repositories.dataSlave.order.OrderAssignSlave;
import com.newframe.repositories.dataSlave.order.OrderFunderSlave;
import com.newframe.repositories.dataSlave.order.OrderHirerSlave;
import com.newframe.repositories.dataSlave.order.OrderRenterSlave;
import com.newframe.services.http.OkHttpService;
import com.newframe.services.order.OrderService;
import com.newframe.services.userbase.ConfigRateService;
import com.newframe.services.userbase.UserBaseInfoService;
import com.newframe.services.userbase.UserFunderService;
import com.newframe.services.userbase.UserHirerService;
import com.newframe.utils.ExcelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

/**
 * @author WangBin
 */
@Async
@Service
public class AsyncTimeoutOrder {

    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private OkHttpService okHttpService;
    @Autowired
    private OrderAssignSlave orderAssignSlave;
    @Autowired
    private UserHirerService userHirerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRenterSlave orderRenterSlave;
    @Autowired
    private UserFunderService userFunderService;
    @Autowired
    private OrderHirerSlave orderHirerSlave;
    @Autowired
    private ConfigRateService configRateService;
    @Autowired
    private OrderFunderSlave orderFunderSlave;

    /**
     * 发送短信提醒
     * @param assigns
     */
    @Async
    public void sendMsg(List<OrderAssign> assigns){
        for (OrderAssign assign : assigns) {
            Long examineUid = assign.getExamineUid();
            UserBaseInfo baseInfo = userBaseInfoService.findOne(examineUid);
            okHttpService.sendSmallMessage(baseInfo.getPhoneNumber(), "");
        }
    }

    /**
     * 订单超时处理
     * @param assigns
     */
    @Async
    public void timeout(List<OrderAssign> assigns){
        for (OrderAssign assign : assigns) {
            timeoutHandle(assign);
        }
    }

    /**
     * 超时订单处理
     * @param orderAssign
     */
    private void timeoutHandle(OrderAssign orderAssign){
        if(!refuseOrder(orderAssign.getOrderId(), orderAssign.getExamineUid(), orderAssign.getOrderType())){
            return;
        }
        OrderAssignQuery query = new OrderAssignQuery();
        query.setOrderType(orderAssign.getOrderType());
        List<OrderAssign> userAssigns = orderAssignSlave.findAll(query);
        if(userAssigns.size() > 2){
            return;
        }
        if(orderAssign.getOrderType().equals(OrderType.LESSOR_ORDER.getCode())){
            renterRent(userAssigns, orderAssign);
        }else{
            renterFinancingBuy(userAssigns, orderAssign);
        }
    }

    private boolean refuseOrder(Long uid, Long orderId, Integer orderType){
        try {
            JsonResult jsonResult = orderType.equals(OrderType.LESSOR_ORDER.getCode())
                    ? orderService.lessorRefuse(orderId, uid)
                    : orderService.funderRefuse(orderId, uid);
                return jsonResult.getCode().equals("200");
        }catch (Exception e){
            return false;
        }
    }

    private void renterFinancingBuy(List<OrderAssign> userAssigns, OrderAssign orderAssign){
        List<Long> examineUids = userAssigns.stream().map(OrderAssign::getExamineUid).collect(toList());
        List<UserFunder> funders = userFunderService.findAll();
        List<Long> funderUids = funders.stream().map(UserFunder::getUid).collect(toList());
        List<Long> uids = funderUids.stream().filter(item -> !examineUids.contains(item)).collect(toList());

        Long funderUid = CollectionUtils.isEmpty(uids)
                ? examineUids.get(new Random().nextInt(examineUids.size()))
                : uids.get(new Random().nextInt(uids.size()));

        OrderFunder orderFunder = orderFunderSlave.findOne(orderAssign.getOrderId());

        FinanceApplyDTO financeApplyDTO = new FinanceApplyDTO();
        financeApplyDTO.setOrderId(orderFunder.getOrderId());
        financeApplyDTO.setSupplierId(orderFunder.getSupplierId());
        financeApplyDTO.setFinancingAmount(orderFunder.getFinancingAmount());
        financeApplyDTO.setFinancingDeadline(orderFunder.getNumberOfPeriods());
        financeApplyDTO.setResidualScheme(orderFunder.getResidualScheme());
        financeApplyDTO.setAveragePrincipal(orderFunder.getAveragePrincipal());
        financeApplyDTO.setOnePrincipal(orderFunder.getOnePrincipal());
        financeApplyDTO.setSumAmount(orderFunder.getSumAmount());
        try {
            orderService.renterFinancingBuy(financeApplyDTO, orderAssign.getRentUid());
        }catch (Exception e){}
    }

    private void renterRent(List<OrderAssign> userAssigns, OrderAssign orderAssign){
        List<Long> examineUids = userAssigns.stream().map(OrderAssign::getExamineUid).collect(toList());
        OrderRenter order = orderRenterSlave.findOne(orderAssign.getOrderId());
        ProductInfoDTO productInfoDTO = new ProductInfoDTO();
        productInfoDTO.setProductBrand(order.getProductBrand());
        productInfoDTO.setProductName(order.getProductName());
        productInfoDTO.setProductColor(order.getProductColor());
        productInfoDTO.setProductStorage(order.getProductStorage());
        productInfoDTO.setProductRandomMemory(order.getProductRandomMemory());
        List<ProductLessor> productLessors = orderService.getLessorProductList(productInfoDTO);
        List<Long> uids = productLessors.stream().map(ProductLessor::getSupplierId).collect(toList());
        List<Long> uidList = uids.stream().filter(item -> !examineUids.contains(item)).distinct().collect(toList());
        if(CollectionUtils.isEmpty(uidList)){
            int nextInt = new Random().nextInt(userAssigns.size());
            OrderAssign assign = userAssigns.get(nextInt);
            OrderHirer orderHirer = orderHirerSlave.findOne(assign.getOrderId());
            try {
                orderService.renterRent(orderHirer.getMerchantId(), orderHirer.getOrderId(), orderHirer.getLessorId(),
                        orderHirer.getNumberOfPeriods(), orderHirer.getMonthlyPayment(), orderHirer.getAccidentBenefit(),
                        orderHirer.getPatternPayment(), orderHirer.getOrderAmount());
            }catch (Exception e){}
        }else{
            OrderHirer orderHirer = orderHirerSlave.findOne(orderAssign.getOrderId());
            int nextInt = new Random().nextInt(productLessors.size());
            ProductLessor productLessor = productLessors.get(nextInt);
            productLessor.getSupplierId();
            int numberOfPeriods = 24;
            double rate = configRateService.getRate().doubleValue();
            BigDecimal accidentBenefit = BigDecimal.ZERO;
            BigDecimal fullRepayAmount = productLessor.getSupplyPrice();
            BigDecimal monthlyPayment = ExcelUtils.pmt(rate / 12, numberOfPeriods, fullRepayAmount.doubleValue());
            try {
                orderService.renterRent(orderAssign.getRentUid(), orderAssign.getOrderId(), orderAssign.getExamineUid(),
                        numberOfPeriods, monthlyPayment, accidentBenefit, orderHirer.getPatternPayment(), fullRepayAmount);
            }catch (Exception e){}

        }
    }
}
