package com.newframe.services.order;

import com.newframe.controllers.JsonResult;
import com.newframe.dto.order.request.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单接口
 * @author kfm
 */
public interface OrderService {

    /**
     * 获取租赁商所有订单
     * @param param 查询参数
     * @param uid 租赁商uid
     * @return jsonResult
     */
    JsonResult getRenterOrder(QueryOrderDTO param, Long uid);

    /**
     * 租赁商融资购机
     * @param uid 租赁商uid
     * @param orderId 订单id
     * @param supplierId 供应商id
     * @return 处理结果
     */
    JsonResult renterFinancingBuy(Long uid, List<Long> orderId, Long supplierId);

    /**
     * 租赁商租机
     * @param uid  租赁商id
     * @param orderId 订单id
     * @param lessorId 租赁商选择的出租方id
     * @param tenancyTerm 租机期限
     * @param downPayment 首付租金
     * @param accidentBenefit 意外保险
     * @param patternPayment 支付方式
     * @return 处理结果
     */
    JsonResult renterRent(Long uid, Long orderId, Long lessorId, Integer tenancyTerm, BigDecimal downPayment, BigDecimal accidentBenefit, Integer patternPayment);

    /**
     * 租赁商取消订单
     * @param orderId 订单id
     * @return 处理结果
     */
    JsonResult cancelOrder(List<Long> orderId);

    /**
     * 租赁商查看订单详情
     * @param orderId 订单id
     * @param renterId 租赁商id
     * @return 处理结果
     */
    JsonResult renterViewDetail(Long orderId, Long renterId);

    /**
     * 按产品信息查询有此机型的供应商列表
     * @param productInfo 产品信息
     * @return 查询结果
     */
    JsonResult getSupplierList(ProductInfoDTO productInfo);

    /**
     * 按产品信息查询有此机型的出租方列表
     * @param productInfo 产品信息
     * @return 查询结果
     */
    JsonResult getLessorList(ProductInfoDTO productInfo);

    /**
     * 查询资金方订单
     * @param param 查询参数
     * @param uid 资金方uid
     * @return 查询结果
     */
    JsonResult getFunderOrder(FunderQueryOrderDTO param, Long uid);

    /**
     * 查询资金方订单详情
     * @param orderId 订单id
     * @param uid 资金方id
     * @return 查询结果
     */
    JsonResult funderViewDetail(Long orderId, Long uid);

    /**
     * 资金方拒绝融资订单请求
     * @param orderId 订单id
     * @param uid 资金方uid
     * @return 返回结果
     */
    JsonResult funderRefuse(Long orderId, Long uid);

    /**
     * 资金方放款
     * @param loanDTO 放款信息
     * @param uid 资金方uid
     * @return 操作结果
     */
    JsonResult funderLoan(LoanDTO loanDTO, Long uid);

    /**
     * 资金方确认线下放款，上传放款凭证
     * @param uid 资金方id
     * @param orderId 订单id
     * @param file 凭证图片
     * @return 返回结果
     */
    JsonResult funderUploadEvidence(Long uid, Long orderId, MultipartFile file);

    /**
     * 查询供应商订单
     * @param param 查询参数
     * @param uid 供应商id
     * @return 返回结果
     */
    JsonResult getsSupplierOrder(QueryOrderDTO param, Long uid);

    /**
     * 供应商发货
     * @param uid 供应商uid
     * @param deliverInfo 发货信息
     * @return 返回结果
     */
    JsonResult supplierDeliver(Long uid, DeliverInfoDTO deliverInfo);
}
