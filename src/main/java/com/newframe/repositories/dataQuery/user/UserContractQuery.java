package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserContract;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserContract.class)
public class UserContractQuery extends BaseQuery {

    @QBindAttrField(fieldName = "uid", where = Where.equal)
    private Long uid;
}