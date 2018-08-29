package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.UserFunder;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

import java.util.List;

@Data
@QBindEntity(entityClass = UserFunder.class)
public class UserFunderQuery extends BaseQuery {

    @QBindAttrField(fieldName = "uid", where = Where.equal)
    private Long uid;

    @QBindAttrField(fieldName = "uid", where = Where.in)
    private List<Long> uids;

    @QBindAttrField(fieldName = "phoneNumber", where = Where.equal)
    private String phoneNumber;

    @QBindAttrField(fieldName = "roleStatus", where = Where.equal)
    private Integer status;

    @QBindAttrField(fieldName = "merchantName", where = Where.like)
    private String merchantName;

    @QBindAttrField(fieldName = "legalEntity", where = Where.equal)
    private String legalEntity;

    @QBindAttrField(fieldName = "legalEntityIdNumber", where = Where.equal)
    private String legalEntityIdNumber;

    @QBindAttrField(fieldName = "ctime", where = Where.greaterThanOrEqualTo)
    private Integer startTime;

    @QBindAttrField(fieldName = "ctime", where = Where.lessThanOrEqualTo)
    private Integer endTime;

    @QBindAttrField(fieldName = "isWhite", where = Where.equal)
    private Boolean isWhite;
}