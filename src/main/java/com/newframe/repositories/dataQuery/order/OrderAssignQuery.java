package com.newframe.repositories.dataQuery.order;

import com.newframe.entity.order.OrderAssign;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
@QBindEntity(entityClass = OrderAssign.class)
public class OrderAssignQuery extends BaseQuery {

    @QBindAttrField(fieldName = "orderStatus", where = Where.equal)
    private Integer orderStatus;

    @QBindAttrField(fieldName = "orderType", where = Where.equal)
    private Integer orderType;
}
