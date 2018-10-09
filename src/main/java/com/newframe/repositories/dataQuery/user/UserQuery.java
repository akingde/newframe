package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.User;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author:wangdong
 * @description:
 */
@QBindEntity(entityClass = User.class)
@Data
public class UserQuery extends BaseQuery {

    /**
     * 手机号码
     */
    @QBindAttrField(fieldName = "mobile", where = Where.equal)
    private String mobile;
}
