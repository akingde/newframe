package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.ConfigRate;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
@QBindEntity(entityClass = ConfigRate.class)
public class ConfigRateQuery extends BaseQuery {
}
