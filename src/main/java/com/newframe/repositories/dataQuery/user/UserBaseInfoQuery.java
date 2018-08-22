package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserBaseInfo;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

@Data
@QBindEntity(entityClass = UserBaseInfo.class)
public class UserBaseInfoQuery  extends BaseQuery {

    @QBindAttrField(fieldName = "uid", where = Where.equal)
    private Long uid;

    @QBindAttrField(fieldName = "phoneNumber", where = Where.equal)
    private Long mobile;
}