package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 供应商销售账户
 * </p>
 *
 * @author zww
 * @since 2018-08-31
 */
@Data
@Entity
public class AccountSupplierSell implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 供应商uid
     */
    private Long uid;
    /**
     * 关联订单ID
     */
    private Long orderId;
    /**
     * 产品信息
     */
    private String productInfo;
    /**
     * 购买价款
     */
    private BigDecimal price;
    /**
     * 购机方
     */
    private Long renterId;
    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;


    /**
     * 累计营收
     */
    private BigDecimal totalEarning;
    /**
     * 累计销售数量
     */
    private Integer saleNumber;
    /**
     * 待发货数量
     */
    private Integer deliverNumber;

}
