package com.newframe.repositories.dataQuery.bank;

import com.newframe.entity.bank.BankMoneyFlow;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@QBindEntity(entityClass = BankMoneyFlow.class)
public class BankMoneyFlowQuery extends BaseQuery {
    @QBindAttrField(fieldName = "bankTransactionNo", where = Where.in)
    private List<String> bankTransactionNos = new ArrayList<>();

    @QBindAttrField(fieldName = "status", where = Where.equal)
    private Integer status;
}
