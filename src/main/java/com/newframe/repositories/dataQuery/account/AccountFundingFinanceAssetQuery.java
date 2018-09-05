package com.newframe.repositories.dataQuery.account;

import com.newframe.entity.account.AccountFundingFinanceAsset;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author zww
 */
@Data
@QBindEntity(entityClass = AccountFundingFinanceAsset.class)
public class AccountFundingFinanceAssetQuery extends BaseQuery {
    @QBindAttrField(fieldName = "uid", where = Where.equal)
    private Long uid;
    @QBindAttrField(fieldName = "orderStatus", where = Where.equal)
    private Integer orderStatus;
}
