package com.newframe.repositories.dataQuery.order;

import com.newframe.entity.order.OrderHirer;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

import java.util.List;

/**
 * @author kfm
 * @date 2018.08.29 15:00
 */
@Data
@QBindEntity(entityClass = OrderHirer.class)
public class OrderHirerQuery extends BaseQuery {
    @QBindAttrField(fieldName = "orderId",where = Where.equal)
    private Long orderId;

    @QBindAttrField(fieldName = "lessorId",where = Where.equal)
    private Long lessorId;

    @QBindAttrField(fieldName = "orderStatus",where = Where.equal)
    private Integer orderStatus;

    @QBindAttrField(fieldName = "orderStatus",where = Where.in)
    private List<Integer> orderStatuses;

    @QBindAttrField(fieldName = "deleteStatus",where = Where.equal)
    private Integer deleteStatus;

}
