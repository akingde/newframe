package com.newframe.controllers.api;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.dto.merchant.order.MerchantInfoDTO;
import com.newframe.dto.merchant.order.MerchantOrderDTO;
import com.newframe.dto.merchant.order.OrderInfoDTO;
import com.newframe.dto.merchant.order.ReletDTO;
import com.newframe.services.merchant.order.MerchantOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 第三方订单交互接口
 * @author kfm
 * @date 2018.09.14 11:35
 */
@RestController
@RequestMapping("/rent/api/merchant/order")
public class MerchantOrderController extends BaseController {
    @Autowired
    MerchantOrderService merchantOrderService;
    @RequestMapping("push")
    public JsonResult pushOrder(@Valid @RequestBody MerchantOrderDTO merchantOrder){
        OperationResult<Boolean> result = merchantOrderService.pushOrder(merchantOrder);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return new JsonResult(result.getErrorCode(),false);
    }

    @RequestMapping("/get")
    public JsonResult getOrderInfo(@Valid @RequestBody MerchantInfoDTO merchantInfo){
        OperationResult<OrderInfoDTO> result = merchantOrderService.getOrderInfo(merchantInfo);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return new JsonResult(result.getErrorCode(),false);
    }

    @RequestMapping("/relet")
    public JsonResult relet(@Valid @RequestBody ReletDTO reletDTO){
        OperationResult<Boolean> result = merchantOrderService.relet(reletDTO);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return new JsonResult(result.getErrorCode(),false);
    }

    public JsonResult getAddress(@Valid @RequestBody MerchantInfoDTO merchantInfo){
        OperationResult<String> result = merchantOrderService.getAddress(merchantInfo);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return new JsonResult(result.getErrorCode(),false);
    }
}
