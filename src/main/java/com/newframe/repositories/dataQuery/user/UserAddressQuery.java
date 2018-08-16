package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserAddress;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
@QBindEntity(entityClass = UserAddress.class)
public class UserAddressQuery extends BaseQuery {
}
