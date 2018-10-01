package com.newframe.entity.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table order_assign
 *
 * @mbggenerated do_not_delete_during_merge
 */
@Entity
@Data
public class OrderAssign {
    /**
     * 指派id
     * id
     */
    @Id
    private Long id;

    /**
     * 订单id
     * order_id
     */
    private Long orderId;

    /**
     * 租赁商uid
     * rent_uid
     */
    private Long rentUid;

    /**
     * 审核人uid
     * examine_uid
     */
    private Long examineUid;

    /**
     * 订单类型
     * order_type
     */
    private Integer orderType;

    /**
     * ctime
     */
    private Integer ctime;

    /**
     * utime
     */
    private Integer utime;
}