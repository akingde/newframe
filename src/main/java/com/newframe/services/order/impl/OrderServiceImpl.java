package com.newframe.services.order.impl;

import com.newframe.controllers.JsonResult;
import com.newframe.controllers.PageJsonResult;
import com.newframe.dto.order.request.QueryOrderDTO;
import com.newframe.dto.order.response.OrderRenterDTO;
import com.newframe.entity.order.OrderRenter;
import com.newframe.enums.SystemCode;
import com.newframe.enums.order.OrderSort;
import com.newframe.repositories.dataMaster.order.OrderRenterMaser;
import com.newframe.repositories.dataQuery.order.OrderRenterQuery;
import com.newframe.repositories.dataSlave.order.OrderRenterSlave;
import com.newframe.services.order.OrderService;
import com.newframe.utils.query.QueryToSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


}
