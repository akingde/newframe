package com.newframe.controllers.api;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.dto.merchant.order.*;
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

    @RequestMapping("/address")
    public JsonResult getAddress(@Valid @RequestBody MerchantInfoDTO merchantInfo){
        OperationResult<AddressDTO> result = merchantOrderService.getAddress(merchantInfo);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return new JsonResult(result.getErrorCode(),false);
    }

    @RequestMapping("/repayNotice")
    public JsonResult repayNotice(@Valid @RequestBody RepayNoticeDTO repayNotice){
        OperationResult<String> result = merchantOrderService.repayNotice(repayNotice);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return new JsonResult(result.getErrorCode(),false);
    }

    @RequestMapping("/status/push")
    public JsonResult pushStatus(PushOrderStatusDTO dto){
        OperationResult<String> result = merchantOrderService.pushStatus(dto);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return new JsonResult(result.getErrorCode(),false);
    }
}
