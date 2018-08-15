package com.newframe.services.order.impl;

import com.newframe.controllers.JsonResult;
import com.newframe.dto.order.QueryOrderDTO;
import com.newframe.entity.order.OrderRenter;
import com.newframe.enums.SystemCode;
import com.newframe.enums.order.OrderSort;
import com.newframe.repositories.dataMaster.order.OrderRenterMaser;
import com.newframe.repositories.dataQuery.order.OrderRenterQuery;
import com.newframe.repositories.dataSlave.order.OrderRenterSlave;
import com.newframe.services.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        if (null == param.getPageSize() || null == param.getPageSize()){
            return new JsonResult(SystemCode.NO_PAGE_PARAM);
        }
        // 查询排序规则
        Sort sort = null;
        if (param.getSort() == null || OrderSort.DESC.getValue().equals(param.getSort())){
            sort = new Sort(Sort.Direction.DESC,OrderRenter.CTIME);
        }else{
            sort = new Sort(Sort.Direction.ASC,OrderRenter.CTIME);
        }
        // 设置查询条件
        OrderRenterQuery orderRenterQuery = new OrderRenterQuery();
        orderRenterQuery.setUid(uid);
        if(null != param.getOrderStatus()){
            orderRenterQuery.setOrderStatus(param.getOrderStatus());
        }
        // 设置分页参数
        Pageable pageable = PageRequest.of(param.getCurrentPage()-1,param.getPageSize(),sort);
//        Page<OrderRenter> orderRenters = orderRenterSlave.findAll(orderRenterQuery);
        return null;
    }


}
