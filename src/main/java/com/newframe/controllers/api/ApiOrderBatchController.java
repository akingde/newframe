package com.newframe.controllers.api;

import com.newframe.common.anony.Anonymous;
import com.newframe.common.exception.AccountOperationException;
import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.dto.common.OrdersBuyParam;
import com.newframe.dto.common.OrdersRentParam;
import com.newframe.dto.common.UidOrderIds;
import com.newframe.dto.order.request.FinanceApplyDTO;
import com.newframe.dto.order.request.ProductInfoDTO;
import com.newframe.dto.order.response.SupplierInfoDTO;
import com.newframe.entity.order.OrderRenter;
import com.newframe.entity.user.ProductLessor;
import com.newframe.entity.user.ProductSupplier;
import com.newframe.entity.user.UserHirer;
import com.newframe.entity.user.UserSupplier;
import com.newframe.enums.SystemCode;
import com.newframe.enums.order.OrderResultEnum;
import com.newframe.repositories.dataQuery.order.OrderProductSupplierQuery;
import com.newframe.repositories.dataSlave.order.OrderRenterSlave;
import com.newframe.repositories.dataSlave.user.ProductLessorSlave;
import com.newframe.repositories.dataSlave.user.ProductSupplierSlave;
import com.newframe.repositories.dataSlave.user.UserHirerSlave;
import com.newframe.repositories.dataSlave.user.UserSupplierSlave;
import com.newframe.services.order.OrderBaseService;
import com.newframe.services.order.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单批量操作的Controller
 */
@RestController
@RequestMapping("/rent/api/orders")
public class ApiOrderBatchController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserSupplierSlave userSupplierSlave;
    @Autowired
    private UserHirerSlave userHirerSlave;
    @Autowired
    private ProductSupplierSlave productSupplierSlave;
    @Autowired
    private ProductLessorSlave productLessorSlave;
    @Autowired
    private OrderRenterSlave orderRenterSlave;
    @Autowired
    private OrderBaseService orderBaseService;


    /**
     * 1出租方-批量发货
     * 出租方发货
     *
     * @return 返回结果
     */
    @Anonymous(true)
    @PostMapping("lessor/batch/deliver")
    public JsonResult lessorBatchLogistics(Long uid, MultipartFile file) throws AccountOperationException {
        if (uid == null) {
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = orderService.lessorBatchLogistics(uid, file);
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 供应商-批量发货
     * 供应商发货
     * 批量发货怎么填写订单物流信息？
     *
     * @return 操作结果
     */
    @Anonymous(true)
    @PostMapping("supplier/batch/deliver")
    public JsonResult supplierBatchDeliver(Long uid, MultipartFile file) {
        if (uid == null) {
            return error(SystemCode.NEED_LOGIN);
        }
        OperationResult<Boolean> result = orderService.supplierBatchDeliver(uid, file);
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 资金方-批量拒绝
     *
     * @return 操作结果
     */
    @Anonymous(true)
    @PostMapping("funder/batch/refuse")
    public JsonResult funderBatchRefuse(@RequestBody @Valid UidOrderIds uidOrderIds) throws AccountOperationException {
        OperationResult<Boolean> result = orderService.funderBatchRefuse(uidOrderIds.getUid(), uidOrderIds.getOrderIds());
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getErrorCode());
    }

    /**
     * 查询有货的供货商
     *
     * @return
     * @throws AccountOperationException
     */
    @Anonymous(true)
    @PostMapping("renter/buy/suppliers")
    public JsonResult getSupplierList(@RequestBody @Valid UidOrderIds uidOrderIds) {
        if (CollectionUtils.isEmpty(uidOrderIds.getOrderIds())) {
            return error(SystemCode.BAD_REQUEST);
        }
        List<OrderRenter> list = orderRenterSlave.findAllById(uidOrderIds.getOrderIds());

        Set<Long> supplierIds = null;
        for (OrderRenter orderRenter : list) {
            OrderProductSupplierQuery query = new OrderProductSupplierQuery();
            query.setProductBrand(orderRenter.getProductBrand());
            query.setProductColor(orderRenter.getProductColor());
            query.setProductStorage(orderRenter.getProductStorage());
            query.setProductName(orderRenter.getProductName());
            List<ProductSupplier> products = productSupplierSlave.findAll(query);
            Set<Long> set = products.stream().map(ProductSupplier::getSupplierId).collect(Collectors.toSet());
            if (null == supplierIds) {
                //第一次增加
                supplierIds.addAll(set);
            } else {
                //取交集
                supplierIds.retainAll(set);
            }
        }
        if (CollectionUtils.isEmpty(supplierIds)) {
            return error(OrderResultEnum.SUPPLIER_NO_PRODUCT);
        }
        List<UserSupplier> listUserSupplier = userSupplierSlave.findAllById(supplierIds);
        List<Map<String, String>> mapList = listUserSupplier.stream().map(userSupplier -> {
            Map<String, String> map = new HashMap();
            map.put("uid", String.valueOf(userSupplier.getUid()));
            map.put("name", userSupplier.getMerchantName());
            return map;
        }).collect(Collectors.toList());
        return success(mapList);
    }

    /**
     * 租赁商-批量融资购机
     *
     * @return
     * @throws AccountOperationException
     */
    @Anonymous(true)
    @PostMapping("renter/buy")
    public JsonResult renterBuy(@RequestBody @Valid OrdersBuyParam ordersBuyParam) throws AccountOperationException {
        for (Long orderId : ordersBuyParam.getOrderIds()) {
            OrderRenter orderRenter = orderService.getRenterOrderById(orderId);
            ProductInfoDTO productInfo = new ProductInfoDTO();
            BeanUtils.copyProperties(orderRenter, productInfo);
            SupplierInfoDTO dto = orderService.getSupplierOrderBuy(orderId, ordersBuyParam.getSupplierId());
            if (null == dto) {
                return new JsonResult("401016",
                        "供应商:" + ordersBuyParam.getSupplierId() + ",没有商品:" + productInfo);
            }
            FinanceApplyDTO financeApply = new FinanceApplyDTO();
            BeanUtils.copyProperties(dto, financeApply);
            financeApply.setOrderId(orderId);
            financeApply.setFinancingDeadline(orderRenter.getNumberOfPayments());
            financeApply.setResidualScheme(dto.getAccidentBenefit().intValue());

            JsonResult jsonResult = orderService.renterFinancingBuy(financeApply, ordersBuyParam.getUid());
            if (!"200".equals(jsonResult.getCode())) {
                return jsonResult;
            }
        }
        return success(true);
    }


    /**
     * 查询有货的出租商
     *
     * @return
     * @throws AccountOperationException
     */
    @Anonymous(true)
    @PostMapping("renter/rent/lessors")
    public JsonResult getLessorList(@RequestBody @Valid UidOrderIds uidOrderIds) {
        List<OrderRenter> list = orderRenterSlave.findAllById(uidOrderIds.getOrderIds());
        Set<Long> supplierIds = null;
        for (OrderRenter orderRenter : list) {
            OrderProductSupplierQuery query = new OrderProductSupplierQuery();
            query.setProductBrand(orderRenter.getProductBrand());
            query.setProductColor(orderRenter.getProductColor());
            query.setProductStorage(orderRenter.getProductStorage());
            query.setProductName(orderRenter.getProductName());
            List<ProductLessor> products = productLessorSlave.findAll(query);
            Set<Long> set = products.stream().map(ProductLessor::getSupplierId).collect(Collectors.toSet());
            if (null == supplierIds) {
                //第一次增加
                supplierIds = new HashSet<>();
                supplierIds.addAll(set);
            } else {
                //取交集
                supplierIds.retainAll(set);
            }
        }
        if (CollectionUtils.isEmpty(supplierIds)) {
            return error(OrderResultEnum.SUPPLIER_NO_PRODUCT);
        }
        List<UserHirer> listUserLessor = userHirerSlave.findAllById(supplierIds);
        List<Map<String, String>> mapList = listUserLessor.stream().map(userLessor -> {
            Map<String, String> map = new HashMap();
            map.put("uid", String.valueOf(userLessor.getUid()));
            map.put("name", userLessor.getMerchantName());
            return map;
        }).collect(Collectors.toList());
        return success(mapList);
    }

    /**
     * 租赁商-批量租机
     * <p>
     * 支付方式，1：全款支付，2：分期支付
     *
     * @return 处理结果
     */
    @Anonymous(true)
    @PostMapping("renter/rent")
    public JsonResult renterRent(@RequestBody @Valid OrdersRentParam ordersRentParam) throws AccountOperationException {
        for (Long orderId : ordersRentParam.getOrderIds()) {
            OrderRenter orderRenter = orderService.getRenterOrderById(orderId);

            OrderProductSupplierQuery query = new OrderProductSupplierQuery();
            query.setSupplierId(ordersRentParam.getLessorId());
            query.setProductBrand(orderRenter.getProductBrand());
            query.setProductColor(orderRenter.getProductColor());
            query.setProductStorage(orderRenter.getProductStorage());
            query.setProductName(orderRenter.getProductName());
            List<ProductLessor> products = productLessorSlave.findAll(query);
            if (CollectionUtils.isEmpty(products)) {
                return new JsonResult("401014",
                        "出租方:" + ordersRentParam.getLessorId() + ",没有商品:" + query);
            }
            ProductLessor productLessor = products.get(0);

            Integer tenancyTerm = 24;
            BigDecimal monthlyPayment = orderBaseService.getRentPrice(productLessor.getSupplyPrice(), new BigDecimal("0.15"), 24);
            BigDecimal accidentBenefit = BigDecimal.ZERO;
            BigDecimal fullRepayAmount = productLessor.getSupplyPrice();
            JsonResult jsonResult = orderService.renterRent(ordersRentParam.getUid(), orderId, ordersRentParam.getLessorId(), tenancyTerm, monthlyPayment, accidentBenefit, ordersRentParam.getPatternPayment(), fullRepayAmount);
            if (!"200".equals(jsonResult.getCode())) {
                return jsonResult;
            }
        }

        return success(true);
    }

    /**
     * 租赁商批量取消订单
     */
    @Anonymous(true)
    @PostMapping("renter/cancel")
    public JsonResult cancelOrder(@RequestBody @Valid UidOrderIds uidOrderIds) {
        return orderService.cancelOrder(uidOrderIds.getOrderIds(), uidOrderIds.getUid());
    }
}
