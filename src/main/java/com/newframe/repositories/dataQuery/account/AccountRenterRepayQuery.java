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

    @QBindAttrField(fieldName = "uid",where = Where.equal)
    private Long uid;

    //查询比月底小的
    @QBindAttrField(fieldName = "payTime",where = Where.lessThanOrEqualTo)
    private Integer lastDayOfMonth;

    //比月初大
    @QBindAttrField(fieldName = "payTime",where = Where.greaterThanOrEqualTo)
    private Integer firstDayOfMonth;


    //比月初大
    @QBindAttrField(fieldName = "withhold",where = Where.equal)
    private Integer withhold;
}
