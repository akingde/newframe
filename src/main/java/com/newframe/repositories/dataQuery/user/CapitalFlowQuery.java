package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.CapitalFlow;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

import static com.newframe.utils.query.Where.*;

/**
 * @author WangBin
 */
@Data
@QBindEntity(entityClass = CapitalFlow.class)
public class CapitalFlowQuery extends BaseQuery {

    @QBindAttrField(fieldName = "orderId", where = equal)
    private Long orderId;

    @QBindAttrField(fieldName = "uid", where = equal)
    private Long uid;

    @QBindAttrField(fieldName = "bankFlowId", where = equal)
    private String bankFlowId;

    @QBindAttrField(fieldName = "bankMoneyFlowId", where = equal)
    private Long bankMoneyFlowId;

    @QBindAttrField(fieldName = "orderStatus", where = equal)
    private Integer orderStatus;

    @QBindAttrField(fieldName = "merchantName", where = like)
    private String merchantName;

    @QBindAttrField(fieldName = "userPhoneNumber", where = equal)
    private String phoneNumber;

    @QBindAttrField(fieldName = "ctime", where = greaterThanOrEqualTo)
    private Integer startTime;

    @QBindAttrField(fieldName = "ctime", where = lessThanOrEqualTo)
    private Integer endTime;
}
