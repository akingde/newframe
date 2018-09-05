package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserRoleApply;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

import java.util.List;

@Data
@QBindEntity(entityClass = UserRoleApply.class)
public class UserRoleApplyQuery extends BaseQuery {

    @QBindAttrField(fieldName = "uid", where = Where.equal)
    private Long uid;

    @QBindAttrField(fieldName = "roleId", where = Where.equal)
    private Integer roleId;

    @QBindAttrField(fieldName = "id", where = Where.equal)
    private Long id;

    @QBindAttrField(fieldName = "merchantName", where = Where.like)
    private String merchantName;

    @QBindAttrField(fieldName = "legalEntity", where = Where.equal)
    private String legalEntity;

    @QBindAttrField(fieldName = "phoneNumber", where = Where.equal)
    private String phoneNumber;

    @QBindAttrField(fieldName = "applyStatus", where = Where.equal)
    private Integer applyStatus;

    @QBindAttrField(fieldName = "ctime", where = Where.greaterThanOrEqualTo)
    private Integer startTime;

    @QBindAttrField(fieldName = "ctime", where = Where.lessThanOrEqualTo)
    private Integer endTime;

    @QBindAttrField(fieldName = "applyStatus", where = Where.in)
    private List<Integer> status;
}