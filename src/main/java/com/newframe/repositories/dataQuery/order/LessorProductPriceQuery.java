package com.newframe.repositories.dataQuery.order;

import com.newframe.entity.order.LessorProductPrice;
import com.newframe.entity.user.ProductLessor;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author kfm
 * @date 2018.08.31 14:09
 */
@QBindEntity(entityClass = LessorProductPrice.class)
@Data
public class LessorProductPriceQuery extends BaseQuery {
    @QBindAttrField(fieldName = "productBrand",where = Where.equal)
    private String productBrand;
    @QBindAttrField(fieldName = "productName",where = Where.equal)
    private String productName;
    @QBindAttrField(fieldName = "productStorage",where = Where.equal)
    private Integer productStorage;

    @QBindAttrField(fieldName = "productRandomMemory",where = Where.equal)
    private Integer productRandomMemory;

    @QBindAttrField(fieldName = "productColor",where = Where.equal)
    private String productColor;

    @QBindAttrField(fieldName = "paymentNumber",where = Where.equal)
    private Integer paymentNumber;
}
