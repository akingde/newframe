package com.newframe.repositories.dataQuery.user;

import com.newframe.entity.user.MerchantAppoint;
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
@QBindEntity(entityClass = MerchantAppoint.class)
public class MerchantAppointQuery extends BaseQuery {

    @QBindAttrField(fieldName = "id", where = Where.equal)
    private Long id;

    @QBindAttrField(fieldName = "rentMerchantUid", where = Where.equal)
    private Long rentMerchantUid;

    @QBindAttrField(fieldName = "supplierUid", where = Where.in)
    private List<Long> supplierUids;
}
