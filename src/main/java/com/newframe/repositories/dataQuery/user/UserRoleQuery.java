package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserRole;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserRole.class)
public class UserRoleQuery extends BaseQuery {
}