package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserSupplier;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserSupplier.class)
public class UserSupplierQuery extends BaseQuery {
}