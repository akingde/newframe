package com.newframe.services.merchant.order.impl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.merchant.order.MerchantOrderDTO;
import com.newframe.entity.order.OrderRenter;
import com.newframe.entity.user.UserRentMerchant;
import com.newframe.enums.SystemCode;
import com.newframe.enums.merchant.MerchantResult;
import com.newframe.enums.order.OrderRenterStatus;
import com.newframe.repositories.dataMaster.order.OrderRenterMaser;
import com.newframe.repositories.dataQuery.order.OrderRenterQuery;
import com.newframe.repositories.dataSlave.order.OrderRenterSlave;
import com.newframe.services.merchant.order.MerchantOrderBaseService;
import com.newframe.services.merchant.order.MerchantOrderService;
import com.newframe.services.userbase.UserRentMerchantService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

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
}
