package com.newframe.repositories.dataQuery.order;

import com.newframe.entity.order.OrderSupplier;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

import java.util.List;

/**
 * @author kfm
 * @date 2018.08.27 17:43
 */
@Data
@QBindEntity(entityClass = OrderSupplier.class)
public class OrderSupplierQuery extends BaseQuery {

    @QBindAttrField(fieldName = "supplierId",where = Where.equal)
    private Long supplierId;

    @QBindAttrField(fieldName = "orderId",where = Where.equal)
    private Long orderId;

    @QBindAttrField(fieldName = "deleteStatus",where = Where.equal)
    private Integer deleteStatus;

    @QBindAttrField(fieldName = "orderStatus",where = Where.in)
    private List<Integer> orderStatuses;

    @QBindAttrField(fieldName = "orderStatus",where = Where.equal)
    private Integer orderStatus;
}
