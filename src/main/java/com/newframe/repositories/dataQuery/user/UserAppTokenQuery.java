package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserAppToken;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserAppToken.class)
public class UserAppTokenQuery extends BaseQuery {
}