package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserPwd;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * UserLoginHistory 查询类
 * Created by wd on 17-7-18.
 */

@QBindEntity(entityClass = UserPwd.class)
@Data
public class UserPwdQuery extends BaseQuery {

    @QBindAttrField(fieldName = "uid", where = Where.equal)
    private Long uid;

}
