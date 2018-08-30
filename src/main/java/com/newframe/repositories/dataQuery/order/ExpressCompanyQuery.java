package com.newframe.repositories.dataQuery.order;

import com.newframe.entity.order.ExpressCompany;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.BaseRepository;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

/**
 * @author kfm
 * @date 2018.08.28 17:12
 */
@Data
@QBindEntity(entityClass = ExpressCompany.class)
public class ExpressCompanyQuery extends BaseQuery {
    @QBindAttrField(fieldName = "id",where = Where.equal)
    private Integer id;
    @QBindAttrField(fieldName = "companyName",where = Where.equal)
    private String companyName;
    @QBindAttrField(fieldName = "companyCode",where = Where.equal)
    private String companyCode;
}
