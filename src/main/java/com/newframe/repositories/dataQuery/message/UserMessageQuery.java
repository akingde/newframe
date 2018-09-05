package com.newframe.repositories.dataQuery.message;

import com.newframe.entity.account.AccountRenterAppointSupplier;
import com.newframe.entity.message.UserMessage;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author kfm
 */
@Data
@QBindEntity(entityClass = UserMessage.class)
public class UserMessageQuery extends BaseQuery {

    @QBindAttrField(fieldName = "uid",where = Where.equal)
    private Long uid;
}
