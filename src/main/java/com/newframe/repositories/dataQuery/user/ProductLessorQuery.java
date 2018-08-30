package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.ProductLessor;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
@QBindEntity(entityClass = ProductLessor.class)
public class ProductLessorQuery extends BaseQuery {

    @QBindAttrField(fieldName = "id", where = Where.equal)
    private Long Id;

    @QBindAttrField(fieldName = "supplierId", where = Where.equal)
    private Long uid;
}