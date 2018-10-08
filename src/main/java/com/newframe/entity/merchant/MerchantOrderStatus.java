package com.newframe.entity.merchant;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author kfm
 * @date 2018.10.02 11:01
 */
@Data
@Entity
public class MerchantOrderStatus {
    /**
     * 平台id
     */
    private Long partnerId;
    /**
     * 平台订单id
     */
    private String partnerOrderId;
    /**
     * 易租订单id
     */
    @Id
    private Long orderId;
    /**
     * 订单状态码
     */
    private String orderStatus;
    /**
     * 订单状态含义
     */
    private String value;
    /**
     * 租赁商id
     */
    private Long renterId;
    private Integer ctime;
    private Integer utime;
}
