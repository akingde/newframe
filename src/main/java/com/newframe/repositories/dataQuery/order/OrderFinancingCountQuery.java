package com.newframe.repositories.dataQuery.order;

import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderFunderPK;
import com.newframe.utils.query.BaseQuery;
import com.newframe.utils.query.Where;
import com.newframe.utils.query.annotation.QBindAttrField;
import com.newframe.utils.query.annotation.QBindEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author kfm
 * @date 2018年8月17日 11点18分
 * 订单融资次数查询
 */
@QBindEntity(entityClass = OrderFunder.class)
@Data
public class OrderFinancingCountQuery extends BaseQuery {
    @QBindAttrField(fieldName = "orderFunderPK.orderId",where = Where.equal)
    private OrderFunderPK orderId;
//    @QBindAttrField(fieldName = "funderId",where = Where.equal)
//    private Long funderId;


}
