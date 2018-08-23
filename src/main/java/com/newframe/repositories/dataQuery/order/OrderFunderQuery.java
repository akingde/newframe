package com.newframe.repositories.dataQuery.order;

import com.newframe.entity.order.OrderFunder;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author kfm
 */
@Data
@QBindEntity(entityClass = OrderFunder.class)
public class OrderFunderQuery extends BaseQuery {
    @QBindAttrField(fieldName = "funderId", where = Where.equal)
    private Long funderId;

    @QBindAttrField(fieldName = "merchantName", where = Where.like)
    private String merchantName;

    @QBindAttrField(fieldName = "orderStatus", where = Where.equal)
    private Integer orderStatus;

    @QBindAttrField(fieldName = "orderId", where = Where.equal)
    private Long orderId;

    @QBindAttrField(fieldName = "deleteStatus", where = Where.equal)
    private Integer deleteStatus;
}
