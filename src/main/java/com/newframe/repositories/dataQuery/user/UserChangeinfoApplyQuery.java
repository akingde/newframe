package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserChangeinfoApply;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserChangeinfoApply.class)
public class UserChangeinfoApplyQuery extends BaseQuery {
}