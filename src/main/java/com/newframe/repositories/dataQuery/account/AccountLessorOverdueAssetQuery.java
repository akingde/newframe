package com.newframe.repositories.dataQuery.account;

import com.newframe.entity.account.AccountLessorOverdueAsset;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author kfm
 */
@Data
@QBindEntity(entityClass = AccountLessorOverdueAsset.class)
public class AccountLessorOverdueAssetQuery extends BaseQuery {

    @QBindAttrField(fieldName = "orderStatus", where = Where.equal)
    private Integer orderStatus;

    @QBindAttrField(fieldName = "uid",where = Where.equal)
    private Long uid;
}
