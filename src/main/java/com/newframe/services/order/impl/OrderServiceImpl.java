package com.newframe.services.order.impl;

import com.newframe.controllers.JsonResult;
import com.newframe.controllers.PageJsonResult;
import com.newframe.dto.order.request.QueryOrderDTO;
import com.newframe.dto.order.response.OrderRenterDTO;
import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderRenter;
import com.newframe.enums.SystemCode;
import com.newframe.enums.order.OrderSort;
import com.newframe.enums.order.OrderRenterStatus;
import com.newframe.repositories.dataMaster.order.OrderFunderMaser;
import com.newframe.repositories.dataMaster.order.OrderRenterMaser;
import com.newframe.repositories.dataQuery.order.OrderRenterQuery;
import com.newframe.repositories.dataSlave.order.OrderFunderSlave;
import com.newframe.repositories.dataSlave.order.OrderRenterSlave;
import com.newframe.services.order.OrderService;
import com.newframe.utils.log.GwsLogger;
import com.newframe.utils.query.QueryToSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 租赁商订单
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

    @Override
    public JsonResult getRenterOrder(QueryOrderDTO param, Long uid) {
        if (null == param.getPageSize() || null == param.getCurrentPage()){
            return new JsonResult(SystemCode.NO_PAGE_PARAM);
        }
        // 查询排序规则
        Sort sort ;
        if (param.getSort() == null || OrderSort.DESC.getValue().equals(param.getSort())){
            sort = new Sort(Sort.Direction.DESC,OrderRenter.CTIME);
        }else{
            sort = new Sort(Sort.Direction.ASC,OrderRenter.CTIME);
        }
        // 设置查询条件
        OrderRenterQuery orderRenterQuery = new OrderRenterQuery();
        orderRenterQuery.setUid(uid);
        orderRenterQuery.setDeleteStatus(OrderRenter.NO_DELETE_STATUS);
        if(null != param.getOrderStatus()){
            orderRenterQuery.setOrderStatus(param.getOrderStatus());
        }
        // 设置分页参数
        Pageable pageable = PageRequest.of(param.getCurrentPage()-1,param.getPageSize(),sort);
        // 包装查询参数
        Specification specification = new QueryToSpecification(orderRenterQuery);
        Page<OrderRenter> orderRenterPage = orderRenterSlave.findAll(specification,pageable);
        // 封装DTO
        List<OrderRenter> orderRenters = orderRenterPage.getContent();
        List<OrderRenterDTO> orderRenterDTOS = new ArrayList<>();
        for(OrderRenter orderRenter:orderRenters){
            OrderRenterDTO orderRenterDTO = new OrderRenterDTO();
            BeanUtils.copyProperties(orderRenter,orderRenterDTO);
            orderRenterDTO.setUid(orderRenter.getUid());
            orderRenterDTO.setOrderTime(orderRenter.getCtime());
            orderRenterDTO.setConsumerName(orderRenter.getUserRealname());
            orderRenterDTO.setConsumerPhone(orderRenter.getUserMobile());
            orderRenterDTO.setConsumerIdentityNumber(orderRenter.getUserIdNumber());
            orderRenterDTO.setConsumerCreditScore(orderRenter.getUserCreditScore());
            orderRenterDTO.setRentDeadlineMonth(orderRenter.getNumberOfPayments());
            orderRenterDTO.setRentDeadlineDay(orderRenter.getNumberOfPayments() * 30);
            orderRenterDTO.setConsumerCreditLine(orderRenter.getUserCreditLine());
            orderRenterDTOS.add(orderRenterDTO);
        }
        return new PageJsonResult(SystemCode.SUCCESS,orderRenterDTOS,orderRenterPage.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult renterFinancingBuy(Long uid, List<Long> orders, Integer supplierId) {
        // 参数校验
        if(orders == null || orders.size() == 0 || supplierId == null){
            return new JsonResult(SystemCode.BAD_REQUEST);
        }
        // todo 根据uid查出租赁商信息
        String renterName = "小米租赁旗舰店";
        // todo 查询供应商是否存在

        // todo 查询资金方uid
        Long funderId = 10086L;
        // 遍历融资订单
        List<OrderFunder> orderFunders = new ArrayList<>();
        for (Long orderId : orders){
            Optional<OrderRenter> optional = orderRenterSlave.findById(orderId);
            if(optional.isPresent()){
                OrderRenter orderRenter = optional.get();
                OrderFunder orderFunder = new OrderFunder();
                BeanUtils.copyProperties(orderRenter,orderFunder);
                orderFunder.setMerchantId(uid);
                orderFunder.setMerchantName(renterName);
                orderFunder.setOrderStatus(OrderRenterStatus.WATIING_FUNDER_AUDIT.getCode());
                orderFunders.add(orderFunder);
                //修改租赁商订单状态，改为待资金方审核
                orderRenterMaser.updateOrderStatus(OrderRenterStatus.WATIING_FUNDER_AUDIT.getCode(),orderId);
            }
        }
        // 生成资金方订单
        orderFunderMaser.saveAll(orderFunders);
        GwsLogger.getLogger().info("租赁商"+uid+"的订单"+orders+"已派发给资金方"+funderId);
        return new JsonResult(SystemCode.SUCCESS,true);
    }


}
