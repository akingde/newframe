package com.newframe.repositories.dataQuery.account;

import com.newframe.entity.account.AccountRenterRent;
import com.newframe.entity.account.AccountRenterRepay;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author kfm
 */
@Data
@QBindEntity(entityClass = AccountRenterRepay.class)
public class AccountRenterRepayQuery extends BaseQuery {

    @QBindAttrField(fieldName = "orderId",where = Where.equal)
    private Long orderId;
}
