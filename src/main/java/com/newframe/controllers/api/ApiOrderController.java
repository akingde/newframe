package com.newframe.controllers.api;

import com.newframe.common.anony.Anonymous;
import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.dto.order.request.*;
import com.newframe.dto.order.response.*;
import com.newframe.entity.order.FinancingInfo;
import com.newframe.enums.SystemCode;
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
@RequestMapping("/rent/api/order")

public class ApiOrderController extends BaseController {

    @Autowired
    OrderService orderService;

    /**
     * 1、租赁商-查询订单列表
     * 查询租赁商订单列表
     * @param param 查询参数
     * @return 查询结果
     * 测试OK
     */
    @Anonymous(true)
    @RequestMapping("renter/getList")
    public JsonResult getRenterOrder(QueryOrderDTO param,Long uid){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.getRenterOrder(param, uid);
    }

    /**
     * 5、租赁商-融资购机
     * 租赁商融资购机
     * 将租赁商订单转到资金方订单去审核
     * @return 处理结果
     */
    @Anonymous(true)
    @RequestMapping("renter/financing/buy")
    public JsonResult renterFinancingBuy(Long orderId,Long supplierId,Long uid,BigDecimal financingAmount,Integer financingDeadline){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.renterFinancingBuy(uid,orderId,supplierId,financingAmount,financingDeadline);
    }

    /**
     * 6、租赁商-租机
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
    public JsonResult renterRent(Long uid,Long orderId, Long lessorId, Integer tenancyTerm, BigDecimal downPayment,BigDecimal accidentBenefit,Integer patternPayment){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.renterRent(uid,orderId,lessorId,tenancyTerm ,downPayment ,accidentBenefit ,patternPayment );
    }

    /**
     * 19、租赁商查看订单详情
     * 租赁商查看订单详情
     * @param orderId 订单id
     * @return 处理结果
     */
    @Anonymous(true)
    @RequestMapping("renter/view/detail")
    public JsonResult renterViewDetail(Long uid,Long orderId){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.renterViewDetail(orderId, uid);
    }

    /**
     * 9、租赁商-取消订单
     * 租赁商取消订单
     * @return 操作结果
     */
    @Anonymous(true)
    @RequestMapping("renter/cancel")
    public JsonResult cancelOrder(@RequestParam List<Long> orderId,Long uid){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        // todo 取消订单应该有uid条件
        return orderService.cancelOrder(orderId);
    }

    /**
     * 7、融资购机-查询供应商列表（单个订单操作）
     * 租赁商进行融资购机时需要查询有订单所需机型的供应商列表
     * @return 查询结果
     */
    @RequestMapping("/getSupplierList")
    public JsonResult getSupplierList(ProductInfoDTO productInfo){
        return orderService.getSupplierList(productInfo);
    }

    /**
     * 8、租赁商租机-查询出租方列表
     * 租赁商进行租机时需要查询有订单所需机型的出租方列表（单个订单操作）
     * @return 查询结果
     */
    @RequestMapping("/getLessorList")
    public JsonResult getLessorList(ProductInfoDTO productInfo){
        return orderService.getLessorList(productInfo);
    }

    //****************资金方订单********************
    /**
     * 2、资金方-查询订单列表
     * 查询资金方订单
     * @return 返回结果
     * 测试OK
     */
    @Anonymous(true)
    @RequestMapping("funder/getList")
    public JsonResult getFunderOrder(FunderQueryOrderDTO param,Long uid){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.getFunderOrder(param, uid);
    }

    /**
     * 20、资金方查看订单详情
     * 查看资金方订单详情
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("funder/view/detail")
    public JsonResult funderViewDetail(Long orderId,Long uid){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.funderViewDetail(orderId,uid);
    }

    /**
     * 10、资金方-拒绝融资订单
     * 资金方审核订单不通过
     * @param orderId 订单id
     * @param reason 不通过原因
     * @return 操作结果
     */
    @RequestMapping("funder/refuse")
    public JsonResult funderRefuse(Long orderId,String reason,Long uid){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.funderRefuse(orderId,uid);
    }

    /**
     * 11、资金方-线下付款确认下单
     * 资金方线下放款(审核通过)
     * 线下放款，要确认线下放款成功后才生成供应商订单（放款凭证可以不上传）
     * @param loanDTO 放款信息
     * @return 操作结果
     */
    @Anonymous(true)
    @RequestMapping("funder/loan")
    public JsonResult funderLoan(LoanDTO loanDTO,Long uid){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.offlineLoan(loanDTO,uid);
    }

    /**
     * 资金方线上付款
     * @param loanDTO 放款dto
     * @param uid 资金方uid
     * @return 放款结果
     */
    @Anonymous(true)
    @RequestMapping("funder/online/loan")
    public JsonResult funderOnlineLoan(LoanDTO loanDTO,Long uid){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.onlineLoan(loanDTO,uid);
    }

    /**
     * 12、资金方-上传放款凭证
     * 确认放款，上传放款凭证
     * @param orderId 订单id
     * @return 操作结果
     */
    @RequestMapping("funder/upload/evidence")
    @Anonymous(true)
    public JsonResult funderUploadEvidence(Long uid,Long orderId,MultipartFile file){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.funderUploadEvidence(uid,orderId,file);
    }
    //************供应商订单****************
    /**
     * 3、供应商-查询订单列表
     * 查询供应商订单
     * @return 查询结果
     * 测试OK
     */
    @RequestMapping("supplier/getList")
    @Anonymous(true)
    public JsonResult getsSupplierOrder(Long uid,QueryOrderDTO param){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.getsSupplierOrder(param,uid );

    }

    /**
     * 26、供应商查看订单详情
     * 查询供应商订单详情
     * @param orderId 订单id
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("supplier/view/detail")
    public JsonResult supplierViewDetail(Long uid,Long orderId){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.supplierViewDetail(orderId,uid);
    }

    /**
     * 13、供应商-发货
     * 供应商发货
     * 批量发货怎么填写订单物流信息？
     * @return 操作结果
     */
    @Anonymous(true)
    @RequestMapping("supplier/deliver")
    public JsonResult supplierDeliver(Long uid,DeliverInfoDTO deliverInfo){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.supplierDeliver(uid,deliverInfo);
    }

    /**
     * 24、供应商和资金方查询物流信息
     * 供应商查看物流信息
     * @return 物流信息
     */
    @Anonymous(true)
    @RequestMapping("supplier/logistics")
    public JsonResult supplierGetLogistics(Long orderId){
        OperationResult<DeliverDTO> result = orderService.supplierGetLogistics(orderId);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    //*****************出租方订单***********************
    /**
     * 4、出租方-查询订单列表
     * 查询出租方订单
     * @return 查询结果
     * 测试OK
     */
    @Anonymous(true)
    @RequestMapping("lessor/getList")
    public JsonResult getLessorOrder(Long uid,QueryOrderDTO param){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.getLessorOrder(uid,param);
    }

    /**
     * 27、出租方查看订单详情
     * 出租方查看订单详情
     * @param orderId 订单id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("lessor/view/detail")
    public JsonResult lessorViewDetail(Long uid,Long orderId){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.lessorViewDetail(uid,orderId);
    }

    /**
     * 14、出租方-发货
     * 出租方发货
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("lessor/deliver")
    public JsonResult lessorLogistics(Long uid,DeliverInfoDTO deliverInfo){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.lessorLogistics(uid,deliverInfo);
    }

    /**
     * 15、出租方-取消订单（拒绝）
     * 出租方取消订单
     * @return 操作结果
     */
    @RequestMapping("lessor/refuse")
    @Anonymous(true)
    public JsonResult lessorRefuse(Long uid,Long orderId){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        return orderService.lessorRefuse(orderId,uid);
    }

    /**
     * 28、查询快递公司列表
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
     * 29、出租方查询物流信息
     * 出租方查询物流信息
     * 物流单号存在hirer_deliver表中
     * @param orderId 订单id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("lessor/logistics")
    public JsonResult lessorLogistics(Long orderId){
        OperationResult<DeliverDTO> result = orderService.lessorGetLogistics(orderId);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 30、租赁商查询物流信息
     * 租赁商查询物流信息
     * 租赁商订单需要判断是租赁订单还是融资购机订单
     * 租赁订单：从hirer_deliver表中查询快递单号
     * 融资购机订单：从order_supplier表中查询快递单号
     * @param orderId 订单id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("renter/logistics")
    public JsonResult renterLogistics(Long orderId){
        OperationResult<DeliverDTO> result = orderService.renterGetLogistics(orderId);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 18、租赁商删除订单
     * 租赁商删除订单
     * 只有已取消的订单可以删除
     * @param uid 租赁商uid
     * @param orderId 订单id
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("renter/delete")
    public JsonResult renterDeleteOrder(Long uid,Long orderId){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = orderService.renterDeleteOrder(uid,orderId);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 16、租赁商-查询租赁商是否可以进行融资购机
     * 判断订单是否可融资
     * 账户余额大于保证金即可
     * 保证金=（手机的供应价-用户租机首付）*15%
     * 手机供应价要读取供应商的商品价格表
     * @param uid 租赁商uid
     * @param orderId 订单id
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("renter/financingable")
    public JsonResult orderFinancingable(Long uid,Long orderId){
        if(uid == null){
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = orderService.orderFinancingable(uid,orderId);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 31、查询不同租期手机的租赁价
     * 查询不同租期平台商品的租赁价格
     * @param productInfoDTO 产品信息
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("renter/getProductPrice")
    public JsonResult getProductPrice(ProductInfoDTO productInfoDTO,Integer paymentNumber){
        OperationResult<LessorProductPriceDTO> result = orderService.getProductPrice(productInfoDTO, paymentNumber);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 21、查询租赁商信息
     * 查询租赁商信息
     * 租赁商信息的逾期次数和融资金额、逾期金额等可以先写死，
     * @param renterId 租赁商id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("renterInfo")
    public JsonResult renterInfo(Long renterId){
        OperationResult<RenterInfo> result = orderService.getRenterInfo(renterId);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 22、查询融资购机信息
     * 查询订单融资信息
     * 查询order_funder
     * 保证金暂时先写死，忘记这个字段了
     * @param orderId 订单id
     * @return 返回结果
     */
    @Anonymous(true)
    @RequestMapping("financingInfo")
    public JsonResult financingInfo(Long orderId){
        OperationResult<FinancingInfo> result = orderService.getFinancingInfo(orderId);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 23、查询租机信息
     * 查询租机信息
     * 查询order_hirer,出租方名称表中没有存，需要从其他地方读取
     * @param orderId 订单id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("rentInfo")
    public JsonResult rentInfo(Long orderId){
        OperationResult<RentInfo> result = orderService.getRentInfo(orderId);
        if(result.getSucc()){
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 25、查询还机地址
     * 查询还机地址
     * @param orderId 订单id
     * @return 查询结果
     */
    @Anonymous(true)
    @RequestMapping("return/address")
    public JsonResult returnAddress(Long orderId){
        return null;
    }
}
