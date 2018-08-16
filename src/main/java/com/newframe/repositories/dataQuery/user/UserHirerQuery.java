package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserHirer;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserHirer.class)
public class UserHirerQuery extends BaseQuery {
}