package com.newframe.repositories.dataQuery.account;

import com.newframe.entity.account.AccountRenterOverdueDetail;
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
@QBindEntity(entityClass = AccountRenterOverdueDetail.class)
public class AccountRenterOverdueQuery extends BaseQuery {

    @QBindAttrField(fieldName = "uid",where = Where.equal)
    private Long uid;
}
