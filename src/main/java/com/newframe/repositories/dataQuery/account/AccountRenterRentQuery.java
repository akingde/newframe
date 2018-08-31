package com.newframe.repositories.dataQuery.account;

import com.newframe.entity.account.AccountRenterRent;
import com.newframe.entity.order.OrderFunder;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

import java.util.List;

/**
 * @author kfm
 */
@Data
@QBindEntity(entityClass = AccountRenterRent.class)
public class AccountRenterRentQuery extends BaseQuery {

    @QBindAttrField(fieldName = "orderStatus", where = Where.equal)
    private Integer orderStatus;

    @QBindAttrField(fieldName = "uid",where = Where.equal)
    private Long uid;
}
