package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.Area;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

import java.util.List;

/**
 * @author WangBin
 */
@Data
@QBindEntity(entityClass = Area.class)
public class AreaQuery extends BaseQuery {

    @QBindAttrField(fieldName = "areaLevel", where = Where.equal)
    private Integer areaLevel;

    @QBindAttrField(fieldName = "areaCode", where = Where.lessThan)
    private Integer maxAreaCode;

    @QBindAttrField(fieldName = "areaCode", where = Where.greaterThan)
    private Integer minAreaCode;

    @QBindAttrField(fieldName = "areaCode", where = Where.in)
    private List<Integer> areaCode;
}
