package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserFunder;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserFunder.class)
public class UserFunderQuery extends BaseQuery {
}