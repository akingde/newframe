package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserPwd;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserPwd.class)
public class UserPwdQuery extends BaseQuery {
}