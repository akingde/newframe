package com.newframe.services.merchant.order.impl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.merchant.order.MerchantOrderDTO;
import com.newframe.entity.order.OrderRenter;
import com.newframe.entity.user.UserRentMerchant;
import com.newframe.enums.SystemCode;
import com.newframe.enums.merchant.MerchantResult;
import com.newframe.enums.order.OrderRenterStatus;
import com.newframe.services.merchant.order.MerchantOrderBaseService;
import com.newframe.services.merchant.order.MerchantOrderService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kfm
 * @date 2018.09.14 11:48
 */
@Service
public class MerchantOrderServiceImpl implements MerchantOrderService {

    @Autowired
    MerchantOrderBaseService merchantOrderBaseService;

    @Autowired
    IdGlobalGenerator idGlobalGenerator;
    @Override
    public OperationResult<Boolean> pushOrder(MerchantOrderDTO merchantOrder) {
        UserRentMerchant userRentMerchant =
                merchantOrderBaseService.getRenterInfoByIdNumber(merchantOrder.getRenterIdNumber(),merchantOrder.getPartnerId());
        if(userRentMerchant == null){
            return new OperationResult<>(MerchantResult.RENTER_IS_NOT_EXIST);
        }
        OrderRenter orderRenter = new OrderRenter();
        BeanUtils.copyProperties(merchantOrder,orderRenter);
        orderRenter.setDeleteStatus(OrderRenter.NO_DELETE_STATUS);
        orderRenter.setOrderStatus(OrderRenterStatus.PENDING.getCode());
        orderRenter.setOrderId(idGlobalGenerator.getSeqId(OrderRenter.class));
        orderRenter.setRenterId(userRentMerchant.getUid());
        orderRenter.setRenterName(userRentMerchant.getMerchantName());
        return new OperationResult<>(SystemCode.SUCCESS,true);
    }
}
