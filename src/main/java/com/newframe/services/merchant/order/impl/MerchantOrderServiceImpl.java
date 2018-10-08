package com.newframe.services.merchant.order.impl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.merchant.order.*;
import com.newframe.entity.merchant.MerchantOrderStatus;
import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderHirer;
import com.newframe.entity.order.OrderRenter;
import com.newframe.entity.user.UserAddress;
import com.newframe.entity.user.UserRentMerchant;
import com.newframe.enums.SystemCode;
import com.newframe.enums.merchant.MerchantResult;
import com.newframe.enums.order.OrderRenterStatus;
import com.newframe.enums.order.OrderType;
import com.newframe.repositories.dataMaster.merchant.MerchantOrderStatusMaster;
import com.newframe.repositories.dataMaster.order.OrderRenterMaser;
import com.newframe.repositories.dataQuery.order.OrderRenterQuery;
import com.newframe.repositories.dataSlave.merchant.MerchantOrderStatusSlave;
import com.newframe.repositories.dataSlave.order.OrderFunderSlave;
import com.newframe.repositories.dataSlave.order.OrderHirerSlave;
import com.newframe.repositories.dataSlave.order.OrderRenterSlave;
import com.newframe.services.merchant.order.MerchantOrderBaseService;
import com.newframe.services.merchant.order.MerchantOrderService;
import com.newframe.services.userbase.UserAddressService;
import com.newframe.services.userbase.UserRentMerchantService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.14 11:48
 */
@Service
public class MerchantOrderServiceImpl implements MerchantOrderService {

    @Autowired
    MerchantOrderBaseService merchantOrderBaseService;

    @Autowired
    UserRentMerchantService userRentMerchantService;

    @Autowired
    OrderRenterMaser orderRenterMaser;

    @Autowired
    OrderRenterSlave orderRenterSlave;

    @Autowired
    OrderHirerSlave orderHirerSlave;

    @Autowired
    OrderFunderSlave orderFunderSlave;

    @Autowired
    MerchantOrderStatusMaster merchantOrderStatusMaster;

    @Autowired
    MerchantOrderStatusSlave merchantOrderStatusSlave;

    @Value("${residual.value.protection.scheme}")
    private BigDecimal residualValue;
    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    IdGlobalGenerator idGlobalGenerator;
    @Override
    public OperationResult<Boolean> pushOrder(MerchantOrderDTO merchantOrder) {
        UserRentMerchant userRentMerchant =
                userRentMerchantService.findOne(merchantOrder.getRenterMobile());
        if(userRentMerchant == null){
            return new OperationResult<>(MerchantResult.RENTER_IS_NOT_EXIST);
        }
        OrderRenterQuery query = new OrderRenterQuery();
        query.setPartnerId(merchantOrder.getPartnerId());
        query.setPartnerOrderId(merchantOrder.getPartnerOrderId());
        if(orderRenterSlave.findOne(query) != null){
            return new OperationResult<>(MerchantResult.MERCHANT_ORDER_EXIST);
        }
        OrderRenter orderRenter = new OrderRenter();
        BeanUtils.copyProperties(merchantOrder,orderRenter);
        orderRenter.setDeleteStatus(OrderRenter.NO_DELETE_STATUS);
        orderRenter.setOrderStatus(OrderRenterStatus.PENDING.getCode());
        orderRenter.setOrderId(idGlobalGenerator.getSeqId(OrderRenter.class));
        orderRenter.setRenterId(userRentMerchant.getUid());
        orderRenter.setRenterName(userRentMerchant.getMerchantName());
        orderRenterMaser.save(orderRenter);
        return new OperationResult<>(SystemCode.SUCCESS,true);
    }

    @Override
    public OperationResult<OrderInfoDTO> getOrderInfo(MerchantInfoDTO merchantInfo) {
        OrderRenterQuery query = new OrderRenterQuery();
        query.setPartnerId(merchantInfo.getPartnerId());
        query.setPartnerOrderId(merchantInfo.getPartnerOrderId());
        OrderRenter orderRenter = orderRenterSlave.findOne(query);
        if(orderRenter == null){
            return new OperationResult<>(MerchantResult.MERCHANT_ORDER_NOT_EXIST);
        }
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setOrderStatus(orderRenter.getOrderStatus());
        // 出租方订单
        if(OrderType.LESSOR_ORDER.getCode().equals(orderRenter.getOrderType())){
            orderInfoDTO.setOrderType(OrderType.LESSOR_ORDER.getCode());
            OrderInfoDTO.RentInfo rentInfo = orderInfoDTO.new RentInfo();
            orderInfoDTO.setRentInfo(rentInfo);
            OrderHirer orderHirer = orderHirerSlave.findOne(orderRenter.getOrderId());
            rentInfo.setAccidentBenefit(orderHirer.getAccidentBenefit());
            rentInfo.setMonthPayment(orderHirer.getMonthlyPayment());
            rentInfo.setPatternPayment(orderHirer.getPatternPayment());
            rentInfo.setNumberOfPeriods(orderHirer.getNumberOfPeriods());
            rentInfo.setOrderAmount(orderHirer.getOrderAmount());
            rentInfo.setDownPayment(orderHirer.getDownPayment());
        }
        // 融资订单
        if(OrderType.FUNDER_ORDER.getCode().equals(orderRenter.getOrderType())){
            orderInfoDTO.setOrderType(OrderType.FUNDER_ORDER.getCode());
            OrderInfoDTO.FinancingInfo financingInfo = orderInfoDTO.new FinancingInfo();
            orderInfoDTO.setFinancingInfo(financingInfo);
            OrderFunder orderFunder = orderFunderSlave.findOne(orderRenter.getOrderId());
            financingInfo.setDeposit(orderFunder.getDeposit());
            financingInfo.setFinancingAmount(orderFunder.getFinancingAmount());
            financingInfo.setNumberOfPeriods(orderFunder.getNumberOfPeriods());
            financingInfo.setResidualScheme(orderFunder.getResidualScheme());
            if(1 == orderFunder.getResidualScheme()){
                financingInfo.setResidualAmount(residualValue);
            }
        }
        return new OperationResult<>(SystemCode.SUCCESS,orderInfoDTO);
    }

    @Override
    public OperationResult<Boolean> relet(ReletDTO reletDTO) {
        return null;
    }

    @Override
    public OperationResult<AddressDTO> getAddress(MerchantInfoDTO merchantInfo) {
        OrderRenterQuery query = new OrderRenterQuery();
        query.setPartnerId(merchantInfo.getPartnerId());
        query.setPartnerOrderId(merchantInfo.getPartnerOrderId());
        OrderRenter orderRenter = orderRenterSlave.findOne(query);
        if(orderRenter == null){
            return new OperationResult<>(MerchantResult.MERCHANT_ORDER_NOT_EXIST);
        }
        UserAddress userAddress = userAddressService.findDefaultAddress(0L);
        if(userAddress != null){
            String address = (userAddress.getProvinceName() == null ? "":(userAddress.getProvinceName()))
                    + (userAddress.getCityName() == null ? "":(userAddress.getCityName()))
                    + (userAddress.getCountyName() == null ? "":(userAddress.getCountyName()))
                    + (userAddress.getCountyName() == null ? "":userAddress.getConsigneeAddress());
            AddressDTO dto = new AddressDTO();
            dto.setAddress(address);
            dto.setAddressType(4);
            dto.setReceiverName(userAddress.getConsigneeName());
            dto.setReceiverPhone(userAddress.getMobile());
            return new OperationResult<>(SystemCode.SUCCESS,dto);
        }
        return new OperationResult<>(MerchantResult.RETURN_ADDRESS_NO_SETTING);
    }

    @Override
    public OperationResult<String> repayNotice(RepayNoticeDTO repayNotice) {
        return null;
    }

    @Override
    public OperationResult<Boolean> pushStatus(PushOrderStatusDTO dto) {
        MerchantOrderStatus merchantOrderStatus = new MerchantOrderStatus();
        OrderRenterQuery query = new OrderRenterQuery();
        query.setPartnerId(dto.getPartnerId());
        query.setPartnerOrderId(dto.getPartnerOrderId());
        OrderRenter orderRenter = orderRenterSlave.findOne(query);
        if(orderRenter == null){
            return new OperationResult<>(MerchantResult.MERCHANT_ORDER_NOT_EXIST);
        }
        merchantOrderStatus.setOrderId(orderRenter.getOrderId());
        merchantOrderStatus.setOrderStatus(dto.getOrderStatus());
        merchantOrderStatus.setPartnerId(dto.getPartnerId());
        merchantOrderStatus.setPartnerOrderId(dto.getPartnerOrderId());
        merchantOrderStatus.setRenterId(orderRenter.getRenterId());
        merchantOrderStatus.setValue(dto.getValue());
        merchantOrderStatusMaster.save(merchantOrderStatus);
        return new OperationResult<>(SystemCode.SUCCESS,true);
    }
}
