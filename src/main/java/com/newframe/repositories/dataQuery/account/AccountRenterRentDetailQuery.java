package com.newframe.repositories.dataQuery.account;

import com.newframe.entity.account.AccountRenterFinancing;
import com.newframe.entity.account.AccountRenterRentDetail;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author kfm
 */
@Data
@QBindEntity(entityClass = AccountRenterRentDetail.class)
public class AccountRenterRentDetailQuery extends BaseQuery {

    @QBindAttrField(fieldName = "payStatus", where = Where.equal)
    private Integer payStatus;

    @QBindAttrField(fieldName = "uid",where = Where.equal)
    private Long uid;

    @QBindAttrField(fieldName = "orderId",where = Where.equal)
    private Long orderId;

    @QBindAttrField(fieldName = "orderStatus", where = Where.equal)
    private Integer orderStatus;


}
