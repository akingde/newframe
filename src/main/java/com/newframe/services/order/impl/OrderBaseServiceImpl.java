package com.newframe.services.order.impl;

import com.newframe.entity.user.UserSupplier;
import com.newframe.services.order.OrderBaseService;
import com.newframe.services.userbase.UserSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kfm
 * @date 2018.09.03 20:33
 */
@Service
public class OrderBaseServiceImpl implements OrderBaseService {

    @Autowired
    private UserSupplierService supplierService;

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

}
