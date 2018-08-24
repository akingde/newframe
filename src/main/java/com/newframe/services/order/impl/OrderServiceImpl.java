package com.newframe.services.order.impl;

import com.newframe.controllers.JsonResult;
import com.newframe.controllers.PageJsonResult;
import com.newframe.dto.order.request.FunderQueryOrderDTO;
import com.newframe.dto.order.request.LoanDTO;
import com.newframe.dto.order.request.ProductInfoDTO;
import com.newframe.dto.order.request.QueryOrderDTO;
import com.newframe.dto.order.response.OrderFunderDTO;
import com.newframe.dto.order.response.OrderRenterDTO;
import com.newframe.entity.order.*;
import com.newframe.enums.SystemCode;
import com.newframe.enums.order.*;
import com.newframe.repositories.dataMaster.order.*;
import com.newframe.repositories.dataQuery.order.OrderFunderQuery;
import com.newframe.repositories.dataQuery.order.OrderRenterQuery;
import com.newframe.repositories.dataSlave.order.*;
import com.newframe.resp.file.CommonResp;
import com.newframe.services.common.AliossService;
import com.newframe.services.http.OkHttpService;
import com.newframe.services.order.OrderService;
import com.newframe.utils.log.GwsLogger;
import com.newframe.utils.query.QueryToSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 租赁商订单
 *
 * @author kfm
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRenterMaser orderRenterMaser;
    @Autowired
    OrderRenterSlave orderRenterSlave;

    @Autowired
    OrderFunderMaser orderFunderMaser;
    @Autowired
    OrderFunderSlave orderFunderSlave;

    @Autowired
    OrderHirerMaser orderHirerMaser;
    @Autowired
    OrderHirerSlave orderHirerSlave;

    @Autowired
    FinancingBuyMachineMaster financingBuyMachineMaster;
    @Autowired
    FinancingBuyMachineSlave financingBuyMachineSlave;

    @Autowired
    OrderFunderEvidenceMaster orderFunderEvidenceMaster;
    @Autowired
    OrderFunderEvidenceSlave orderFunderEvidenceSlave;

    @Autowired
    OrderSupplierMaster orderSupplierMaster;
    @Autowired
    OrderSupplierSlave orderSupplierSlave;

    @Autowired
    OkHttpService okHttpService;
    @Autowired
    private AliossService aliossService;

    @Value("${order.financing.max.times}")
    private Integer maxOrderFinancingTimes;
    @Value("${api.basetool.bucket}")
    private String bucket;
    @Value("${api.besetool.service}")
    private String baseUrl;
    @Value("${api.basetool.singleFileUpload}")
    private String fileUploadUrl;

    @Override
    public JsonResult getRenterOrder(QueryOrderDTO param, Long uid) {
        if (null == param.getPageSize() || null == param.getCurrentPage()) {
            return new JsonResult(SystemCode.NO_PAGE_PARAM);
        }
        // 查询排序规则
        Sort sort;
        if (param.getSort() == null || OrderSort.DESC.getValue().equals(param.getSort())) {
            sort = new Sort(Sort.Direction.DESC, OrderRenter.CTIME);
        } else {
            sort = new Sort(Sort.Direction.ASC, OrderRenter.CTIME);
        }
        // 设置查询条件
        OrderRenterQuery orderRenterQuery = new OrderRenterQuery();
        // 只查询此会话租赁商的订单
        orderRenterQuery.setRenterId(uid);
        orderRenterQuery.setDeleteStatus(OrderRenter.NO_DELETE_STATUS);
        if (null != param.getOrderStatus()) {
            orderRenterQuery.setOrderStatus(param.getOrderStatus());
        }
        // 设置分页参数
        Pageable pageable = PageRequest.of(param.getCurrentPage() - 1, param.getPageSize(), sort);
        // 包装查询参数
        Specification specification = new QueryToSpecification(orderRenterQuery);
        Page<OrderRenter> orderRenterPage = orderRenterSlave.findAll(specification, pageable);
        // 封装DTO
        List<OrderRenter> orderRenters = orderRenterPage.getContent();
        List<OrderRenterDTO> orderRenterDTOS = new ArrayList<>();
        for (OrderRenter orderRenter : orderRenters) {
            OrderRenterDTO orderRenterDTO = new OrderRenterDTO();
            BeanUtils.copyProperties(orderRenter, orderRenterDTO);
            orderRenterDTO.setConsumerUid(orderRenter.getUid());
            orderRenterDTO.setOrderTime(orderRenter.getCtime());
            orderRenterDTO.setConsumerName(orderRenter.getUserRealname());
            orderRenterDTO.setConsumerPhone(orderRenter.getUserMobile());
            orderRenterDTO.setConsumerIdentityNumber(orderRenter.getUserIdNumber());
            orderRenterDTO.setConsumerCreditScore(orderRenter.getUserCreditScore());
            orderRenterDTO.setRentDeadlineMonth(orderRenter.getNumberOfPayments());
            orderRenterDTO.setRentDeadlineDay(orderRenter.getNumberOfPayments() * 30);
            orderRenterDTO.setConsumerCreditLine(orderRenter.getUserCreditLine());
            orderRenterDTO.setConsumerAddress(orderRenter.getUserAddress());
            orderRenterDTOS.add(orderRenterDTO);
        }
        return new PageJsonResult(SystemCode.SUCCESS, orderRenterDTOS, orderRenterPage.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult renterFinancingBuy(Long uid, List<Long> orders, Long supplierId) {
        // 参数校验
        if (orders == null || orders.size() == 0 || supplierId == null) {
            return new JsonResult(SystemCode.BAD_REQUEST);
        }
        // todo 根据uid查出租赁商信息
        String renterName = "小米手机租赁店";
        // todo 查询供应商是否存在

        // todo 查询资金方uid（目前资金方较少，随便查出一个资金方）
        Long funderId = Math.abs(new Random().nextLong());
        // 存放融资失败的订单，需要返回给前端
        List<Map<String, Object>> failOrders = new ArrayList<>();

        List<OrderFunder> orderFunders = new ArrayList<>();
        // 遍历融资订单
        for (Long orderId : orders) {

            // 查询此订单号是否已经在进行资金方审核，防止一个订单提交给多个资金方

            //查询订单融资是否超过3次
            Integer times = orderFunderSlave.getOrderFinancingTimes(orderId);
            // 如果查出来融资次数是0次，则是首次融资
            if (times == null) {
                times = 0;
            }
            if (times >= maxOrderFinancingTimes) {
                Map<String, Object> failOrder = new HashMap<>(2);
                failOrder.put("orderId", orderId);
                failOrder.put("failMessage", SystemCode.ORDER_FINANCING_FAIL.getMessage());
                failOrders.add(failOrder);
                // 将租赁商状态改为不允许融资状态
                orderRenterMaser.updateOrderStatus(OrderRenterStatus.ORDER_FINANCING_OVER_THREE.getCode(), orderId);
                // 超过最大订单融资次数跳出本次循环
                continue;
            }

            // 查询租赁商订单信息，生成资金方订单数据
            Optional<OrderRenter> optional = orderRenterSlave.findById(orderId);
            if (optional.isPresent()) {
                OrderRenter orderRenter = optional.get();
                // 检查订单状态是否是不可融资状态
                if (OrderRenterStatus.ORDER_FINANCING_OVER_THREE.getCode().equals(orderRenter.getOrderStatus())) {
                    Map<String, Object> failOrder = new HashMap<>(2);
                    failOrder.put("orderId", orderId);
                    failOrder.put("failMessage", SystemCode.ORDER_FINANCING_FAIL.getMessage());
                    failOrders.add(failOrder);
                }
                OrderFunder orderFunder = new OrderFunder();
                BeanUtils.copyProperties(orderRenter, orderFunder);
                // 此处操作用户是租赁商，所以资金方的商家id应该是租赁商的id
                orderFunder.setMerchantId(uid);
                orderFunder.setMerchantName(renterName);
                // 资金方的uid是从数据库中查出来的，暂时使用随机数代替
                orderFunder.setFunderId(funderId);
                orderFunder.setOrderStatus(OrderRenterStatus.WATIING_FUNDER_AUDIT.getCode());
                orderFunder.setDispatchTimes(times + 1);
                orderFunder.setSupplierId(supplierId);
                orderFunders.add(orderFunder);
                //修改租赁商订单状态，改为待资金方审核
                orderRenterMaser.updateOrderStatus(OrderRenterStatus.WATIING_FUNDER_AUDIT.getCode(), orderId);
            }
        }
        // 生成资金方订单
        orderFunderMaser.saveAll(orderFunders);
        GwsLogger.getLogger().info("租赁商" + uid + "的订单" + orders + "已派发给资金方" + funderId);
        if (failOrders.size() == 0) {
            return new JsonResult(SystemCode.SUCCESS, true);
        }
        // todo 要不要操作账户表？

        // 返回订单融资失败
        return new JsonResult(SystemCode.ORDER_FINANCING_FAIL, failOrders);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult renterRent(Long uid, Long orderId, Long lessorId, Integer tenancyTerm,
                                 BigDecimal downPayment, BigDecimal accidentBenefit, Integer patternPayment) {
        // 参数校验
        if (orderId == null || lessorId == null
                || tenancyTerm == null || downPayment == null
                || accidentBenefit == null || !PatternPaymentEnum.isDefined(patternPayment)) {
            return new JsonResult(SystemCode.BAD_REQUEST);
        }

        // todo 根据lessorId查询出租方是否存在

        // todo 根据租赁商uid查出租赁商信息
        String renterName = "小米手机租赁店";
        String renterMobile = "15957180382";

        Optional<OrderRenter> optional = orderRenterSlave.findById(orderId);
        if (optional.isPresent()) {
            OrderRenter orderRenter = optional.get();
            OrderHirer orderHirer = new OrderHirer();
            BeanUtils.copyProperties(orderRenter, orderHirer);
            orderHirer.setMerchantId(uid);
            orderHirer.setMerchantName(renterName);
            orderHirer.setMerchantMobile(renterMobile);
            // 出租方订单为 待出租方审核
            orderHirer.setOrderStatus(OrderLessorStatus.WATIING_LESSOR_AUDIT.getCode());
            // 出租方订单的租机价格，意外保险等由平台指定
            orderHirer.setDownPayment(downPayment);
            orderHirer.setAccidentBenefit(accidentBenefit);
            orderHirer.setNumberOfPayments(tenancyTerm);
            orderHirer.setPatternPayment(patternPayment);
            // 修改租赁商订单状态
            orderRenter.setOrderStatus(OrderRenterStatus.WAITING_LESSOR_AUDIT.getCode());
            orderRenterMaser.updateOrderStatus(orderRenter.getOrderStatus(), orderRenter.getOrderId());
            // 生成出租方订单
            orderHirerMaser.save(orderHirer);
        }

        GwsLogger.getLogger().info("租赁商" + uid + "的订单" + orderId + "已派发给资金方：" + lessorId);
        return new JsonResult(SystemCode.SUCCESS, true);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult cancelOrder(List<Long> orders) {
        // 参数校验
        if (orders == null || orders.size() == 0) {
            return new JsonResult(SystemCode.BAD_REQUEST);
        }
        // 遍历取消订单
        for (Long orderId : orders) {
            Optional<OrderRenter> optional = orderRenterSlave.findById(orderId);
            if (optional.isPresent()) {
                OrderRenter orderRenter = optional.get();
                orderRenter.setOrderStatus(OrderRenterStatus.ORDER_CANCEL.getCode());
                OrderRenterQuery query = new OrderRenterQuery();
                query.setOrderId(orderRenter.getOrderId());
                Integer row = orderRenterMaser.update(orderRenter, query, OrderRenter.ORDER_STATUS);
                // 如果修改订单状态失败，说明存在异常，抛出异常回滚
                if (row != 1) {
                    throw new RuntimeException(SystemCode.ORDER_CANCEL_FAIL.getMessage());
                }
            }
        }

        return new JsonResult(SystemCode.SUCCESS, true);
    }

    @Override
    public JsonResult renterViewDetail(Long orderId, Long renterId) {
        if (orderId == null) {
            return new JsonResult(SystemCode.BAD_REQUEST);
        }
        OrderRenterQuery query = new OrderRenterQuery();
        query.setRenterId(renterId);
        query.setDeleteStatus(OrderRenter.NO_DELETE_STATUS);
        query.setOrderId(orderId);
        List<OrderRenter> orderRenters = orderRenterSlave.findAll(query);
        if (orderRenters != null && orderRenters.size() == 1) {
            OrderRenter orderRenter = orderRenters.get(0);
            OrderRenterDTO orderRenterDTO = new OrderRenterDTO();
            BeanUtils.copyProperties(orderRenter, orderRenterDTO);
            orderRenterDTO.setConsumerUid(orderRenter.getUid());
            orderRenterDTO.setOrderTime(orderRenter.getCtime());
            orderRenterDTO.setConsumerName(orderRenter.getUserRealname());
            orderRenterDTO.setConsumerPhone(orderRenter.getUserMobile());
            orderRenterDTO.setConsumerIdentityNumber(orderRenter.getUserIdNumber());
            orderRenterDTO.setConsumerCreditScore(orderRenter.getUserCreditScore());
            orderRenterDTO.setRentDeadlineMonth(orderRenter.getNumberOfPayments());
            orderRenterDTO.setRentDeadlineDay(orderRenter.getNumberOfPayments() * 30);
            orderRenterDTO.setConsumerCreditLine(orderRenter.getUserCreditLine());
            orderRenterDTO.setConsumerAddress(orderRenter.getUserAddress());
            // todo 查出用户坏账次数
            orderRenterDTO.setConsumerBedDebtTimes(new Random().nextInt(10));
            return new JsonResult(SystemCode.SUCCESS, orderRenterDTO);
        }
        return new JsonResult(SystemCode.BAD_REQUEST);
    }

    @Override
    public JsonResult getSupplierList(ProductInfoDTO productInfo) {
        return null;
    }

    @Override
    public JsonResult getLessorList(ProductInfoDTO productInfo) {
        return null;
    }

    @Override
    public JsonResult getFunderOrder(FunderQueryOrderDTO param, Long uid) {
        if (null == param.getPageSize() || null == param.getCurrentPage()) {
            return new JsonResult(SystemCode.NO_PAGE_PARAM);
        }
        // 包装查询条件
        OrderFunderQuery orderFunderQuery = new OrderFunderQuery();
        if (StringUtils.isNotEmpty(param.getRenterName())) {
            orderFunderQuery.setMerchantName(param.getRenterName());
        }
        orderFunderQuery.setFunderId(uid);
        if (param.getOrderStatus() != null) {
            orderFunderQuery.setOrderStatus(param.getOrderStatus());
        }
        // 封装排序
        Sort sort;
        if (OrderSort.DESC.getValue().equals(param.getSort())) {
            sort = new Sort(Sort.Direction.DESC, OrderFunder.CTIME);
        } else {
            sort = new Sort(Sort.Direction.ASC, OrderFunder.CTIME);
        }
        // 设置分页
        Pageable pageable = PageRequest.of(param.getCurrentPage() - 1, param.getPageSize(), sort);
        Specification specification = new QueryToSpecification(orderFunderQuery);
        // 查询数据
        Page<OrderFunder> page = orderFunderSlave.findAll(specification, pageable);
        List<OrderFunder> orderFunders = page.getContent();
        // 封装dto
        List<OrderFunderDTO> orders = new ArrayList<>();
        for (OrderFunder orderFunder : orderFunders) {
            OrderFunderDTO orderFunderDTO = wrapOrderFunder2DTO(orderFunder);
            orders.add(orderFunderDTO);
        }
        return new PageJsonResult(SystemCode.SUCCESS, orders, page.getTotalElements());
    }

    @Override
    public JsonResult funderViewDetail(Long orderId, Long uid) {
        if (orderId == null) {
            return new JsonResult(SystemCode.BAD_REQUEST);
        }
        OrderFunderQuery query = new OrderFunderQuery();
        query.setOrderId(orderId);
        query.setFunderId(uid);
        query.setDeleteStatus(OrderFunder.NO_DELETE_STATUS);
        List<OrderFunder> orderFunders = orderFunderSlave.findAll(query);
        if (orderFunders != null && orderFunders.size() == 1) {
            OrderFunder orderFunder = orderFunders.get(0);
            OrderFunderDTO orderFunderDTO = wrapOrderFunder2DTO(orderFunder);
            return new JsonResult(SystemCode.SUCCESS, orderFunderDTO);
        }
        return new JsonResult(SystemCode.BAD_REQUEST);
    }

    @Override
    public JsonResult funderRefuse(Long orderId, Long uid) {
        if (orderId == null) {
            return new JsonResult(SystemCode.BAD_REQUEST);
        }
        // 查询资金方订单，为了保障安全，只能查出本人的订单
        OrderFunderQuery query = new OrderFunderQuery();
        query.setOrderId(orderId);
        query.setFunderId(uid);
        query.setDeleteStatus(OrderFunder.NO_DELETE_STATUS);
        List<OrderFunder> orderFunders = orderFunderSlave.findAll(query);
        if (orderFunders != null && orderFunders.size() == 1) {
            OrderFunder orderFunder = orderFunders.get(0);
            // 查询租赁商订单，将状态改为资金方已拒绝
            Optional<OrderRenter> orderRenterOptional = orderRenterSlave.findById(orderId);
            if (orderRenterOptional.isPresent()) {
                OrderRenter orderRenter = orderRenterOptional.get();
                orderFunder.setOrderStatus(OrderRenterStatus.FUNDER_AUDIT_REFUSE.getCode());
                orderRenterMaser.save(orderRenter);
                orderFunder.setOrderStatus(OrderFunderStatus.AUDIT_REFUSE.getCode());
                orderFunderMaser.save(orderFunder);
                return new JsonResult(SystemCode.SUCCESS, true);
            }
        }
        return new JsonResult(SystemCode.BAD_REQUEST);
    }

    @Override
    public JsonResult funderLoan(LoanDTO loanDTO, Long uid) {
        if (loanDTO.getLoanAmount() == null || loanDTO.getLoanChannel() == null
                || loanDTO.getLoanModel() == null || loanDTO.getOrderId() == null) {
            return new JsonResult(SystemCode.BAD_REQUEST);
        }
        // 查询资金方订单，为了保障安全，只能查出本人的订单
        OrderFunderQuery query = new OrderFunderQuery();
        query.setOrderId(loanDTO.getOrderId());
        query.setFunderId(uid);
        query.setDeleteStatus(OrderFunder.NO_DELETE_STATUS);
        List<OrderFunder> orderFunders = orderFunderSlave.findAll(query);
        // 只查出一条数据，避免get(0)出错
        if (orderFunders != null && orderFunders.size() == 1) {
            OrderFunder orderFunder = orderFunders.get(0);
            // 查询租赁商订单
            Optional<OrderRenter> optional = orderRenterSlave.findById(loanDTO.getOrderId());
            if (optional.isPresent()) {
                OrderRenter orderRenter = optional.get();
                // 只允许租赁商订单状态为待资金方审核的订单放款
                if (OrderRenterStatus.WATIING_FUNDER_AUDIT.getCode().equals(orderRenter.getOrderStatus())) {
                    boolean success = false;
                    // 线下放款
                    if (LoanChannelEnum.OFFLINE.getValue().equals(loanDTO.getLoanChannel())) {
                        success = offlineLoan(loanDTO, orderFunder, orderRenter);
                    }
                    // 线上放款
                    if (LoanChannelEnum.ONLINE.getValue().equals(loanDTO.getLoanChannel())) {
                        success = onlineLoan(loanDTO, orderFunder, orderRenter);
                    }
                    if (success) {
                        return new JsonResult(SystemCode.SUCCESS);
                    }
                    return new JsonResult(SystemCode.LOAN_FAIL);
                }
                return new JsonResult(SystemCode.LOAN_ORDER_STATUS_ERROR, false);
            }
        }
        return new JsonResult(SystemCode.BAD_REQUEST);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult funderUploadEvidence(Long uid, Long orderId, MultipartFile file) {
        if (orderId == null || file == null) {
            return new JsonResult(SystemCode.BAD_REQUEST);
        }
        String imgUrl;
        try {
            // 上传文件
            imgUrl = aliossService.uploadFileStreamToBasetool(file, bucket);
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResult(SystemCode.FILE_UPLOAD_FAIL);
        }
        // 上传文件的状态码是否为200
        if (imgUrl != null) {
            // 查询资金方订单，为了保障安全，只能查出本人的订单
            OrderFunderQuery query = new OrderFunderQuery();
            query.setOrderId(orderId);
            query.setFunderId(uid);
            query.setDeleteStatus(OrderFunder.NO_DELETE_STATUS);
            List<OrderFunder> orderFunders = orderFunderSlave.findAll(query);
            if (orderFunders != null && orderFunders.size() == 1) {
                OrderFunder orderFunder = orderFunders.get(0);
                // 查询租赁商订单，将状态改为资金方已拒绝
                Optional<OrderRenter> orderRenterOptional = orderRenterSlave.findById(orderId);
                if (orderRenterOptional.isPresent()) {
                    OrderRenter orderRenter = orderRenterOptional.get();
                    // 保存线下放款凭证
                    OrderFunderEvidence evidence = new OrderFunderEvidence();
                    evidence.setOrderId(orderId);
                    evidence.setEvidenceUrl(imgUrl);
                    evidence.setRenterId(orderRenter.getRenterId());
                    evidence.setFunderId(orderFunder.getFunderId());
                    orderFunderEvidenceMaster.save(evidence);
                    // 修改租赁商订单状态为资金方线下放款成功
                    orderRenter.setOrderStatus(OrderRenterStatus.FUNDER_OFFLINE_LOAN_SUCCESS.getCode());
                    orderRenterMaser.save(orderRenter);
                    // 修改资金方订单状态为,线下放款，有凭证，待发货
                    orderFunder.setOrderStatus(OrderFunderStatus.WAITING_DELIVER_EVIDENCE.getCode());
                    orderFunderMaser.save(orderFunder);
                    generateSupplyOrder(orderRenter, orderFunder);
                    return new JsonResult(SystemCode.GENERATE_SUPPLY_ORDER_SUCCESS, true);
                }
            }
        }
        return new JsonResult(SystemCode.BAD_REQUEST, false);
    }

    @Override
    public JsonResult getsSupplierOrder(QueryOrderDTO param) {
        if (null == param.getPageSize() || null == param.getCurrentPage()) {
            return new JsonResult(SystemCode.NO_PAGE_PARAM);
        }
        // 查询排序规则
        Sort sort;
        if (param.getSort() == null || OrderSort.DESC.getValue().equals(param.getSort())) {
            sort = new Sort(Sort.Direction.DESC, OrderRenter.CTIME);
        } else {
            sort = new Sort(Sort.Direction.ASC, OrderRenter.CTIME);
        }
        // 设置查询条件
        return null;
    }

    /**
     * 将orderFunder转换成DTO返回给前端
     *
     * @param orderFunder 从数据库中查出的orderFunder
     * @return dto
     */
    private OrderFunderDTO wrapOrderFunder2DTO(OrderFunder orderFunder) {
        OrderFunderDTO orderFunderDTO = new OrderFunderDTO();
        BeanUtils.copyProperties(orderFunder, orderFunderDTO);
        orderFunderDTO.setOrderTime(orderFunder.getCtime());
        orderFunderDTO.setConsumerName(orderFunder.getUserRealname());
        orderFunderDTO.setConsumerPhone(orderFunder.getUserMobile());
        orderFunderDTO.setConsumerIdentityNumber(orderFunder.getUserIdNumber());
        orderFunderDTO.setConsumerCreditScore(orderFunder.getUserCreditScore());
        orderFunderDTO.setRentDeadlineMonth(orderFunder.getNumberOfPayments());
        orderFunderDTO.setRentDeadlineDay(orderFunder.getNumberOfPayments() * 30);
        orderFunderDTO.setConsumerCreditLine(orderFunder.getUserCreditLine());
        orderFunderDTO.setRenterId(orderFunder.getMerchantId());
        orderFunderDTO.setRenterName(orderFunder.getMerchantName());
        BigDecimal financingAmount = orderFunder.getMonthlyPayment().multiply(
                new BigDecimal(orderFunder.getNumberOfPayments()));
        financingAmount = financingAmount.add(orderFunder.getAccidentBenefit());
        orderFunderDTO.setFinancingAmount(financingAmount);
        return orderFunderDTO;
    }

    /**
     * 资金方线上放款
     *
     * @param loanDTO     放款信息
     * @param orderFunder 资金方订单
     * @param orderRenter 租赁商订单
     * @return 是否成功
     */
    private boolean onlineLoan(LoanDTO loanDTO, OrderFunder orderFunder, OrderRenter orderRenter) {
        // todo 这里应该是要操作账户表的，等先研究一下账户再写
        return false;
    }

    /**
     * 资金方线下放款
     *
     * @param loanDTO     放款信息
     * @param orderFunder 资金方订单
     * @param orderRenter 租赁商订单
     * @return 是否成功
     */
    private boolean offlineLoan(LoanDTO loanDTO, OrderFunder orderFunder, OrderRenter orderRenter) {
        // todo 这里应该是要操作账户表的，等先研究一下账户再写
        return false;
    }

    /**
     * 生成供应商订单并保存到数据库
     *
     * @param orderRenter 租赁商订单
     * @param orderFunder 资金方订单
     */
    private void generateSupplyOrder(OrderRenter orderRenter, OrderFunder orderFunder) {

        OrderSupplier orderSupplier = new OrderSupplier();
        orderSupplier.setOrderId(orderRenter.getOrderId());
        orderSupplier.setPartnerId(orderRenter.getPartnerId());
        orderSupplier.setPartnerOrderId(orderRenter.getPartnerOrderId());
        orderSupplier.setSupplierId(orderFunder.getSupplierId());
        orderSupplier.setMerchantId(orderFunder.getMerchantId());
        orderSupplier.setMerchantName(orderFunder.getMerchantName());
        orderSupplier.setUid(orderFunder.getUid());
        orderSupplier.setReceiverName(orderFunder.getUserRealname());
        orderSupplier.setReceiverAddress(orderRenter.getUserAddress());
        orderSupplier.setReceiverMobile(orderRenter.getUserMobile());
        orderSupplier.setProduceBrand(orderRenter.getProductBrand());
        orderSupplier.setProductName(orderRenter.getProductName());
        orderSupplier.setProductColor(orderRenter.getProductColor());
        orderSupplier.setProductRandomMemory(orderRenter.getProductRandomMemory());
        orderSupplier.setProductStorage(orderRenter.getProductStorage());
        orderSupplier.setDeleteStatus(OrderSupplier.NO_DELETE_STATUS);
        // 供应商还未确认收款，待发货状态
        orderSupplier.setOrderStatus(OrderSupplierStatus.PAYMENTING.getCode());
        orderSupplierMaster.save(orderSupplier);
    }
}
