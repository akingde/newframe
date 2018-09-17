package com.newframe.services.order;

import com.newframe.common.exception.AccountOperationException;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.dto.order.request.*;
import com.newframe.dto.order.response.*;
import com.newframe.entity.order.FinancingInfo;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单接口
 *
 * @author kfm
 */
public interface OrderService {

    /**
     * 获取租赁商所有订单
     *
     * @param param 查询参数
     * @param uid   租赁商uid
     * @return jsonResult
     */
    JsonResult getRenterOrder(QueryOrderDTO param, Long uid);

    /**
     * 获取未完成的租赁商订单数量
     * @param uid
     * @return
     */
    int getNotFininishRenterOrder(Long uid);

    /**
     * 租赁商融资购机
     *
     * @param uid        租赁商uid
     * @param orderId    订单id
     * @param supplierId 供应商id
     * @return 处理结果
     */
    JsonResult renterFinancingBuy(Long uid, Long orderId, Long supplierId, BigDecimal financingAmount, Integer financingDeadline) throws AccountOperationException;

    /**
     * 租赁商租机
     *
     * @param uid             租赁商id
     * @param orderId         订单id
     * @param lessorId        租赁商选择的出租方id
     * @param tenancyTerm     租机期限
     * @param downPayment     首付租金
     * @param accidentBenefit 意外保险
     * @param patternPayment  支付方式
     * @return 处理结果
     */
    JsonResult renterRent(Long uid, Long orderId, Long lessorId, Integer tenancyTerm, BigDecimal downPayment, BigDecimal accidentBenefit, Integer patternPayment);

    /**
     * 租赁商取消订单
     *
     * @param orderId 订单id
     * @return 处理结果
     */
    JsonResult cancelOrder(List<Long> orderId);

    /**
     * 租赁商查看订单详情
     *
     * @param orderId  订单id
     * @param renterId 租赁商id
     * @return 处理结果
     */
    JsonResult renterViewDetail(Long orderId, Long renterId);

    /**
     * 按产品信息查询有此机型的供应商列表
     *
     * @param productInfo 产品信息
     * @return 查询结果
     */
    JsonResult getSupplierList(ProductInfoDTO productInfo);

    /**
     * 按产品信息查询有此机型的出租方列表
     *
     * @param productInfo 产品信息
     * @return 查询结果
     */
    JsonResult getLessorList(ProductInfoDTO productInfo);

    /**
     * 查询资金方订单
     *
     * @param param 查询参数
     * @param uid   资金方uid
     * @return 查询结果
     */
    JsonResult getFunderOrder(FunderQueryOrderDTO param, Long uid);

    /**
     * 查询资金方订单详情
     *
     * @param orderId 订单id
     * @param uid     资金方id
     * @return 查询结果
     */
    JsonResult funderViewDetail(Long orderId, Long uid);

    /**
     * 资金方拒绝融资订单请求
     *
     * @param orderId 订单id
     * @param uid     资金方uid
     * @return 返回结果
     */
    JsonResult funderRefuse(Long orderId, Long uid) throws AccountOperationException;

    /**
     * 资金方放款
     *
     * @param loanDTO 放款信息
     * @param uid     资金方uid
     * @return 操作结果
     */
    JsonResult offlineLoan(LoanDTO loanDTO, Long uid);

    /**
     * 资金方确认线下放款，上传放款凭证
     *
     * @param uid     资金方id
     * @param orderId 订单id
     * @param file    凭证图片
     * @return 返回结果
     */
    JsonResult funderUploadEvidence(Long uid, Long orderId, MultipartFile file) throws AccountOperationException;

    /**
     * 查询供应商订单
     *
     * @param param 查询参数
     * @param uid   供应商id
     * @return 返回结果
     */
    JsonResult getsSupplierOrder(QueryOrderDTO param, Long uid);

    /**
     * 供应商发货
     *
     * @param uid         供应商uid
     * @param deliverInfo 发货信息
     * @return 返回结果
     */
    JsonResult supplierDeliver(Long uid, DeliverInfoDTO deliverInfo);

    /**
     * 供应商查询物流信息
     *
     * @param orderId 订单id
     * @return 物流信息
     */
    OperationResult<DeliverDTO> supplierGetLogistics(Long orderId);

    /**
     * 供应商查看订单详情
     *
     * @param orderId 订单id
     * @param uid     供应商id
     * @return 返回结果
     */
    JsonResult supplierViewDetail(Long orderId, Long uid);

    /**
     * 查询出租方订单列表
     *
     * @param uid   uid
     * @param param 参数
     * @return 返回数据
     */
    JsonResult getLessorOrder(Long uid, QueryOrderDTO param);

    /**
     * 出租方查看订单详情
     *
     * @param uid     出租方id
     * @param orderId 订单id
     * @return 返回结果
     */
    JsonResult lessorViewDetail(Long uid, Long orderId);

    /**
     * 出租方发货
     * @param uid uid
     * @param deliverInfo 发货信息
     * @return 操作结果
     */
    JsonResult lessorLogistics(Long uid, DeliverInfoDTO deliverInfo);

    /**
     * 出租方审核不通过
     * @param orderId 订单id
     * @param uid 用户uid
     * @return 操作结果
     */
    JsonResult lessorRefuse(Long orderId, Long uid);

    /**
     * 查询快递公司列表
     * @return 查询结果
     */
    OperationResult<List<ExpressCompanyDTO>> getExpressList();

    /**
     * 出租方查询物流信息
     * @param orderId 订单id
     * @return 查询结果
     */
    OperationResult<DeliverDTO> lessorGetLogistics(Long orderId);

    /**
     * 租赁商查询物流信息
     * @param orderId 订单id
     * @return 查询结果
     */
    OperationResult<DeliverDTO> renterGetLogistics(Long orderId);

    /**
     * 租赁商删除订单，只能删除已取消的订单
     * @param uid 租赁商uid
     * @param orderId 订单id
     * @return 操作结果
     */
    OperationResult<Boolean> renterDeleteOrder(Long uid, Long orderId);

    /**
     * 判断租赁商订单是否可以融资
     * @param uid 租赁商uid
     * @param orderId 订单id
     * @return 返回结果
     */
    OperationResult<Boolean> orderFinancingable(Long uid, Long orderId);

    /**
     * 查询租赁商信息
     * @param renterId 租赁商ID
     * @return 返回结果
     */
    OperationResult<RenterInfo> getRenterInfo(Long renterId);

    /**
     * 查询融资信息
     * @param orderId 订单id
     * @return 返回结果
     */
    OperationResult<FinancingInfo> getFinancingInfo(Long orderId);

    /**
     * 查询租机信息
     * @param orderId 订单id
     * @return 返回结果
     */
    OperationResult<RentInfo> getRentInfo(Long orderId);

    /**
     * 查询不同租期的机器价格
     * @param productInfoDTO 产品信息
     * @param paymentNumber
     * @return 返回结果
     */
    OperationResult<LessorProductPriceDTO> getProductPrice(ProductInfoDTO productInfoDTO, Integer paymentNumber);

    /**
     * 资金方线上放款
     * @param loanDTO
     * @param uid
     * @return
     */
    JsonResult onlineLoan(LoanDTO loanDTO, Long uid) throws AccountOperationException;
}
