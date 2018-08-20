package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserRole;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserRole.class)
public class UserRoleQuery extends BaseQuery {

    @QBindAttrField(fieldName = "id", where = Where.equal)
    private Long id;

    @QBindAttrField(fieldName = "uid", where = Where.equal)
    private Long uid;

    @QBindAttrField(fieldName = "roleId", where = Where.equal)
    private Integer roleId;

    @QBindAttrField(fieldName = "roleStatus", where = Where.equal)
    private Integer roleStatus;
}