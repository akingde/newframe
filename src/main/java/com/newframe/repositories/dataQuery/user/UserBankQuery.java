package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserBank;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
@QBindEntity(entityClass = UserBank.class)
public class UserBankQuery extends BaseQuery {

    @QBindAttrField(fieldName = "uid", where = Where.equal)
    private Long uid;
}
