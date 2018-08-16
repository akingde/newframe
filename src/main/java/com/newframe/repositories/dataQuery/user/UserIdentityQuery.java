package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserIdentity;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserIdentity.class)
public class UserIdentityQuery extends BaseQuery {
}