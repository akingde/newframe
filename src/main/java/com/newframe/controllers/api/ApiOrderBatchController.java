package com.newframe.controllers.api;

import com.newframe.common.anony.Anonymous;
import com.newframe.common.exception.AccountOperationException;
import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.enums.SystemCode;
import com.newframe.services.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 订单批量操作的Controller
 * @author kfm.zww
 */
@RestController
@RequestMapping("/rent/api/orders")
public class ApiOrderBatchController extends BaseController {

    @Autowired
    private OrderService orderService;

    /**
     * 1出租方-批量发货
     * 出租方发货
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("lessor/batch/deliver")
    public JsonResult lessorBatchLogistics(Long uid, MultipartFile file) throws AccountOperationException {
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = orderService.lessorBatchLogistics(uid,file);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 供应商-批量发货
     * 供应商发货
     * 批量发货怎么填写订单物流信息？
     * @return 操作结果
     */
    @Anonymous(true)
    @RequestMapping("supplier/batch/deliver")
    public JsonResult supplierBatchDeliver(Long uid,MultipartFile file){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = orderService.supplierBatchDeliver(uid,file);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 资金方-批量拒绝
     * @return 操作结果
     */
    @Anonymous(true)
    @RequestMapping("funder/batch/refuse")
    public JsonResult funderBatchRefuse(Long uid, List<Long> orders) throws AccountOperationException {
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = orderService.funderBatchRefuse(uid,orders);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }
}
