package com.newframe.repositories.dataQuery.order;

import com.newframe.entity.order.OrderRenter;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * 租赁商订单查询封装
 * @author kfm
 */
@QBindEntity(entityClass = OrderRenter.class)
@Data
public class OrderRenterQuery extends BaseQuery {

    @QBindAttrField(fieldName = "uid",where = Where.equal)
    private Long uid;

    @QBindAttrField(fieldName = "orderStatus",where = Where.equal)
    private Integer orderStatus;

    @QBindAttrField(fieldName = "deleteStatus",where = Where.equal)
    private Integer deleteStatus;

}
