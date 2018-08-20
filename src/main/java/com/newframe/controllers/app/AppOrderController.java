package com.newframe.controllers.app;

import com.newframe.common.anony.Anonymous;
import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.order.request.DeliverInfoDTO;
import com.newframe.dto.order.request.FunderQueryOrderDTO;
import com.newframe.dto.order.request.ProductInfoDTO;
import com.newframe.dto.order.request.QueryOrderDTO;
import com.newframe.services.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:wangdong
 * @description:订单相关模块的Controller
 */
@RestController
@RequestMapping("/app/order")

public class AppOrderController extends BaseController {

    @Autowired
    OrderService orderService;

    /**
     * 查询租赁商订单列表
     * @param param
     * @return
     */
    @Anonymous(true)
    @RequestMapping("renter/getList")
    public JsonResult getRenterOrder(QueryOrderDTO param){
        // todo 获取登陆用户uid
        Long uid = 1231231L;
        JsonResult result = orderService.getRenterOrder(param, uid);
        return result;
    }

    /**
     * 租赁商融资购机
     * 将租赁商订单转到资金方订单去审核
     * @return
     */
    @Anonymous(true)
    @RequestMapping("renter/financing/buy")
    public JsonResult renterFinancingBuy(@RequestParam List<Long> orderId,Integer supplierId){
        // todo
        Long uid = 1231231L;
        return orderService.renterFinancingBuy(uid,orderId,supplierId);
    }

    /**
     * 租赁商租机
     * @param accidentBenefit 意外保险金额
     * @param orderId 订单id
     * @param  lessorId 出租方id
     * @param tenancyTerm 租期
     * @param downPayment 首付
     * @param patternPayment 支付方式，1：全款支付，2：分期支付
     * @return 处理结果
     */
    @Anonymous(true)
    @RequestMapping("renter/rent")
    public JsonResult renterRent(Long orderId, Long lessorId, Integer tenancyTerm, BigDecimal downPayment,BigDecimal accidentBenefit,Integer patternPayment){
        // todo
        Long uid = 1231231L;
        return orderService.renterRent(uid,orderId,lessorId,tenancyTerm ,downPayment ,accidentBenefit ,patternPayment );
    }

    /**
     * 租赁商查看订单详情
     *
     * @return
     */
    @RequestMapping("renter/view/detail")
    public JsonResult renterViewDetail(Long orderId){
        // todo:文档
        return null;
    }

    /**
     * 租赁商取消订单
     * @return
     */
    @RequestMapping("renter/cancel")
    public JsonResult cancelOrder(@RequestParam List<Long> orderId){
        return null;
    }

    /**
     * 租赁商进行融资购机时需要查询有订单所需机型的供应商列表
     * @return
     */
    @RequestMapping("/getSupplierList")
    public JsonResult getSupplierList(ProductInfoDTO productInfo){
        return null;
    }

    /**
     * 租赁商进行租机时需要查询有订单所需机型的出租方列表（单个订单操作）
     * @return
     */
    @RequestMapping("/getLessorList")
    public JsonResult getLessorList(ProductInfoDTO productInfo){
        return null;
    }

    /****************资金方订单********************/
    /**
     * 查询资金方订单
     * @return
     */
    @RequestMapping("funder/getList")
    public JsonResult getFunderOrder(FunderQueryOrderDTO param){
        return null;
    }

    /**
     * 查看资金方订单详情
     * @return
     */
    @RequestMapping("funder/view/detail")
    public JsonResult funderViewDetail(Long orderId){
        // todo: 文档
        return null;
    }

    /**
     * 资金方审核订单不通过
     * @param orderId
     * @return
     */
    @RequestMapping("funder/refuse")
    public JsonResult funderRefuse(@RequestParam List<Long> orderId){
        return null;
    }

    /**
     * 资金方放款(审核通过)
     * @param orderId
     * @return
     */
    @RequestMapping("funder/loan")
    public JsonResult funderLoan(@RequestParam List<Long> orderId){
        return null;
    }

    /**
     * 资金方确认放款
     * @param orderId
     * @return
     */
    @RequestMapping("funder/confirm/loan")
    public JsonResult funderConfirmLoan(Long orderId){
        return null;
    }

    /**
     * 上传放款凭证
     * @param orderId
     * @return
     */
    @RequestMapping("funder/upload/evidence")
    public JsonResult funderUploadEvidence(Long orderId, MultipartFile file){
        return null;
    }
    /************供应商订单****************/
    /**
     * 查询供应商订单
     * @return
     */
    @RequestMapping("supplier/getList")
    public JsonResult getsSupplierOrder(QueryOrderDTO param){
        return null;
    }

    /**
     * 供应商发货
     * 批量发货怎么填写订单物流信息？
     * @return
     */
    @RequestMapping("supplier/deliver")
    public JsonResult supplierDeliver(DeliverInfoDTO deliverInfo){
        return null;
    }

    /**
     * 供应商查看物流信息
     * @return
     */
    @RequestMapping("supplier/logistics")
    public JsonResult supplierGetLogistics(Long orderId){
        return null;
    }

    /**
     * 供应商编辑物流
     * @return
     */
    @RequestMapping("supplier/edit/logistics")
    public JsonResult supplierEditLogistics(DeliverInfoDTO deliverInfo){
        return null;
    }

    /*****************出租方订单***********************/
    /**
     * 查询出租方订单
     * @return
     */
    @RequestMapping("lessor/getList")
    public JsonResult getLessorOrder(QueryOrderDTO param){
        return null;
    }

    /**
     * 出租方发货
     * @return
     */
    @RequestMapping("lessor/deliver")
    public JsonResult lessorLogistics(DeliverInfoDTO deliverInfo){
        return null;
    }

    /**
     * 出租方取消订单
     * @return
     */
    @RequestMapping("lessor/refuse")
    public JsonResult lessorRefuse(List<Long> orderId){
        return null;
    }

    /**
     * 出租方购机
     * @return
     */
    @RequestMapping("lessor/buy")
    public JsonResult lessorBuy(List<Long> orderId,Long supplierId){
        return null;
    }

}
