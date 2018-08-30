package com.newframe.controllers.api;

import com.newframe.common.anony.Anonymous;
import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.dto.order.request.*;
import com.newframe.dto.order.response.ExpressCompanyDTO;
import com.newframe.services.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangdong
 * 订单相关模块的Controller
 */
@RestController
@RequestMapping("/app/order")

public class ApiOrderController extends BaseController {

    @Autowired
    OrderService orderService;

    /**
     * 查询租赁商订单列表
     * @param param 查询参数
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("renter/getList")
    public JsonResult getRenterOrder(QueryOrderDTO param){
        // todo 获取登陆用户uid
        Long uid = 2L;

        return orderService.getRenterOrder(param, uid);
    }

    /**
     * 租赁商融资购机
     * 将租赁商订单转到资金方订单去审核
     * @return 处理结果
     */
    @Anonymous(true)
    @RequestMapping("renter/financing/buy")
    public JsonResult renterFinancingBuy(@RequestParam List<Long> orderId,Long supplierId){
        // todo
        Long uid = 2L;
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
     * @param orderId 订单id
     * @return 处理结果
     */
    @Anonymous(true)
    @RequestMapping("renter/view/detail")
    public JsonResult renterViewDetail(Long orderId){
        // todo:文档
        Long uid = 2L;
        return orderService.renterViewDetail(orderId, uid);
    }

    /**
     * 租赁商取消订单
     * @return 操作结果
     */
    @Anonymous(true)
    @RequestMapping("renter/cancel")
    public JsonResult cancelOrder(@RequestParam List<Long> orderId){
        return orderService.cancelOrder(orderId);
    }

    /**
     * 租赁商进行融资购机时需要查询有订单所需机型的供应商列表
     * @return 查询结果
     */
    @RequestMapping("/getSupplierList")
    public JsonResult getSupplierList(ProductInfoDTO productInfo){
        return orderService.getSupplierList(productInfo);
    }

    /**
     * 租赁商进行租机时需要查询有订单所需机型的出租方列表（单个订单操作）
     * @return 查询结果
     */
    @RequestMapping("/getLessorList")
    public JsonResult getLessorList(ProductInfoDTO productInfo){
        return orderService.getLessorList(productInfo);
    }

    //****************资金方订单********************
    /**
     * 查询资金方订单
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("funder/getList")
    public JsonResult getFunderOrder(FunderQueryOrderDTO param){
        // todo
        Long uid = 3436672695388700980L;
        return orderService.getFunderOrder(param, uid);
    }

    /**
     * 查看资金方订单详情
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("funder/view/detail")
    public JsonResult funderViewDetail(Long orderId){
        // todo: 文档
        Long uid = 3436672695388700980L;

        return orderService.funderViewDetail(orderId,uid);
    }

    /**
     * 资金方审核订单不通过
     * @param orderId 订单id
     * @param reason 不通过原因
     * @return 操作结果
     */
    @RequestMapping("funder/refuse")
    public JsonResult funderRefuse(Long orderId,String reason){
        // todo 拒绝原因怎么存
        Long uid = 3436672695388700980L;
        return orderService.funderRefuse(orderId,uid);
    }

    /**
     * 资金方放款(审核通过)
     * 线上放款，放款成功即生成供应商订单
     * 线下放款，要确认线下放款成功后才生成供应商订单（放款凭证可以不上传）
     * @param loanDTO 放款信息
     * @return 操作结果
     */
    @Anonymous(true)
    @RequestMapping("funder/loan")
    public JsonResult funderLoan(LoanDTO loanDTO){
        Long uid = 3436672695388700980L;
        return orderService.funderLoan(loanDTO,uid);
    }

    /**
     * 确认放款，上传放款凭证
     * @param orderId 订单id
     * @return 操作结果
     */
    @RequestMapping("funder/upload/evidence")
    @Anonymous(true)
    public JsonResult funderUploadEvidence(Long orderId,@RequestPart(name = "file") MultipartFile file){
        Long uid = 3436672695388700980L;
        return orderService.funderUploadEvidence(uid,orderId,file);
    }
    //************供应商订单****************
    /**
     * 查询供应商订单
     * @return 查询结果
     */
    @RequestMapping("supplier/getList")
    @Anonymous(true)
    public JsonResult getsSupplierOrder(QueryOrderDTO param){
        Long uid = 4L;
        return orderService.getsSupplierOrder(param,uid );

    }

    /**
     * 查询供应商订单详情
     * @param orderId 订单id
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("supplier/view/detail")
    public JsonResult supplierViewDetail(Long orderId){
        Long uid = 4L;
        return orderService.supplierViewDetail(orderId,uid);
    }

    /**
     * 供应商发货
     * 批量发货怎么填写订单物流信息？
     * @return 操作结果
     */
    @Anonymous(true)
    @RequestMapping("supplier/deliver")
    public JsonResult supplierDeliver(DeliverInfoDTO deliverInfo){
        Long uid = 4L;
        return orderService.supplierDeliver(uid,deliverInfo);
    }

    /**
     * 供应商查看物流信息
     * @return 物流信息
     */
    @Anonymous(true)
    @RequestMapping("supplier/logistics")
    public JsonResult supplierGetLogistics(Long orderId){
        Long uid = 4L;
        return orderService.supplierGetLogistics(orderId,uid);
    }

    /**
     * 供应商编辑物流
     * @return 操作结果
     */
    @RequestMapping("supplier/edit/logistics")
    public JsonResult supplierEditLogistics(DeliverInfoDTO deliverInfo){
        Long uid = 4L;
        return null;
    }

    //*****************出租方订单***********************
    /**
     * 查询出租方订单
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("lessor/getList")
    public JsonResult getLessorOrder(QueryOrderDTO param){
        Long uid = 5L;
        return orderService.getLessorOrder(uid,param);
    }

    /**
     * 出租方查看订单详情
     * @param orderId 订单id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("lessor/view/detail")
    public JsonResult lessorViewDetail(Long orderId){
        Long uid = 5L;
        return orderService.lessorViewDetail(uid,orderId);
    }

    /**
     * 出租方发货
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("lessor/deliver")
    public JsonResult lessorLogistics(DeliverInfoDTO deliverInfo){
        Long uid = 5L;
        return orderService.lessorLogistics(uid,deliverInfo);
    }

    /**
     * 出租方取消订单
     * @return 操作结果
     */
    @RequestMapping("lessor/refuse")
    @Anonymous(true)
    public JsonResult lessorRefuse(Long orderId){
        Long uid = 5L;
        return orderService.lessorRefuse(orderId,uid);
    }

    /**
     * 查询快递公司列表
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("express/list")
    public JsonResult getExpressList(){
        OperationResult<List<ExpressCompanyDTO>> result = orderService.getExpressList();
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 出租方查询物流信息
     * 物流单号存在hirer_deliver表中
     * @param uid 出租方uid
     * @param orderId 订单id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("lessor/logistics")
    public JsonResult lessorLogistics(Long uid,Long orderId){
        return null;
    }

    /**
     * 租赁商查询物流信息
     * 租赁商订单需要判断是租赁订单还是融资购机订单
     * 租赁订单：从hirer_deliver表中查询快递单号
     * 融资购机订单：从order_supplier表中查询快递单号
     * @param uid 租赁商uid
     * @param orderId 订单id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("renter/logistics")
    public JsonResult renterLogistics(Long uid,Long orderId){
        return null;
    }

    /**
     * 租赁商删除订单
     * 只有已取消的订单可以删除
     * @param uid 租赁商uid
     * @param orderId 订单id
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("renter/delete")
    public JsonResult renterDeleteOrder(Long uid,Long orderId){
        return null;
    }

    /**
     * 判断订单是否可融资
     * 账户余额大于保证金即可
     * 保证金=（手机的供应价-用户租机首付）*15%
     * 手机供应价要读取供应商的山坡价格表
     * @param uid 租赁商uid
     * @param orderId 订单id
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("renter/financingable")
    public JsonResult orderFinancingable(Long uid,Long orderId){
        return null;
    }

    /**
     * 查询不同租期平台商品的租赁价格
     * @param productInfoDTO 产品信息
     * @param paymentTimes 租期
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("renter/getProductPrice")
    public JsonResult getProductPrice(ProductInfoDTO productInfoDTO,Integer paymentTimes){
        return null;
    }

    /**
     * 查询租赁商信息
     * 租赁商信息的逾期次数和融资金额、逾期金额等可以先写死，
     * @param renterId 租赁商id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("renterInfo")
    public JsonResult renterInfo(Long renterId){
        return null;
    }

    /**
     * 查询订单融资信息
     * 查询order_hirer
     * 保证金暂时先写死，忘记这个字段了
     * @param orderId 订单id
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("financingInfo")
    public JsonResult financingInfo(Long orderId){
        return null;
    }

    /**
     * 查询租机信息
     * 查询order_hirer,出租方名称表中没有存，需要从其他地方读取
     * @param orderId 订单id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("rentInfo")
    public JsonResult rentInfo(Long orderId){
        return null;
    }

    /**
     * 查询还机地址
     * @param orderId 订单id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("returnAddress")
    public JsonResult returnAddress(Long orderId){
        return null;
    }
}
