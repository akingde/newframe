package com.newframe.repositories.dataQuery.order;

import com.newframe.entity.order.OrderRenter;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

import java.util.List;

/**
 * 租赁商订单查询封装
 * @author kfm
 */
@QBindEntity(entityClass = OrderRenter.class)
@Data
public class OrderRenterQuery extends BaseQuery {

    @QBindAttrField(fieldName = "renterId",where = Where.equal)
    private Long renterId;

    @QBindAttrField(fieldName = "orderStatus",where = Where.equal)
    private Integer orderStatus;

    @QBindAttrField(fieldName = "orderStatus",where = Where.in)
    private List<Integer> orderStatuses;

    @QBindAttrField(fieldName = "deleteStatus",where = Where.equal)
    private Integer deleteStatus;

    @QBindAttrField(fieldName = "orderId",where = Where.equal)
    private Long orderId;

}
