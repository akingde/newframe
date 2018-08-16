package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserContract;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserContract.class)
public class UserContractQuery extends BaseQuery {
}