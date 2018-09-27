package com.newframe.repositories.dataQuery.order;

import com.newframe.entity.user.ProductSupplier;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author kfm
 * @date 2018.08.31 14:09
 */
@QBindEntity(entityClass = ProductSupplier.class)
@Data
public class OrderProductSupplierQuery extends BaseQuery {
    @QBindAttrField(fieldName = "supplierId",where = Where.equal)
    private Long supplierId;
    @QBindAttrField(fieldName = "brand",where = Where.like)
    private String productBrand;
    @QBindAttrField(fieldName = "model",where = Where.like)
    private String productName;
    @QBindAttrField(fieldName = "rom",where = Where.equal)
    private Integer productStorage;

    @QBindAttrField(fieldName = "ram",where = Where.equal)
    private Integer productRandomMemory;

    @QBindAttrField(fieldName = "color",where = Where.equal)
    private String productColor;
}
