package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.ProductSupplier;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
@QBindEntity(entityClass = ProductSupplier.class)
public class ProductSupplierQuery extends BaseQuery {

    @QBindAttrField(fieldName = "id", where = Where.equal)
    private Long Id;

    @QBindAttrField(fieldName = "supplierId", where = Where.equal)
    private Long uid;

    @QBindAttrField(fieldName = "isDelated", where = Where.equal)
    private Integer isDelated;
}
