package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserRoleApply;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserRoleApply.class)
public class UserRoleApplyQuery extends BaseQuery {
}