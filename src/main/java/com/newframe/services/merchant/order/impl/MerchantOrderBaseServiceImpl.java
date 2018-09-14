package com.newframe.services.merchant.order.impl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.merchant.order.MerchantOrderDTO;
import com.newframe.entity.user.UserRentMerchant;
import com.newframe.enums.SystemCode;
import com.newframe.repositories.dataQuery.user.UserRentMerchantQuery;
import com.newframe.repositories.dataSlave.user.UserRentMerchantSlave;
import com.newframe.services.merchant.order.MerchantOrderBaseService;
import com.newframe.services.merchant.order.MerchantOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kfm
 * @date 2018.09.14 11:48
 */
@Service
public class MerchantOrderBaseServiceImpl implements MerchantOrderBaseService {

    @Autowired
    UserRentMerchantSlave userRentMerchantSlave;


}
