package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserBaseInfo;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserBaseInfo.class)
public class UserBaseInfoQuery  extends BaseQuery {
}