package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.Area;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
@QBindEntity(entityClass = Area.class)
public class AreaQuery extends BaseQuery {
}
