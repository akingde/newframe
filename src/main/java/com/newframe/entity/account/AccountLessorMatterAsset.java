package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 出租方实物资产
 * </p>
 *
 * @author zww
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountLessorMatterAsset implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @Id
    private Long id;
    /**
     * 出租时间
     */
    private Long rentTime;
    /**
     * 关联订单id
     */
    private Long orderId;
    /**
     * 租赁产品信息
     */
    private String productInfo;
    /**
     * 租金总额
     */
    private BigDecimal totalAmount;
    /**
     * 租机期限
     */
    private BigDecimal rentDeadline;
    /**
     * 租赁物购买价款
     */
    private BigDecimal matterPrice;
    /**
     * 承租人ID
     */
    private Long renterId;
    /**
     * 承租人店铺名称
     */
    private String renterName;
    /**
     * 终端承租人
     */
    private Long finalRenter;
    /**
     * 终端承租人姓名
     */
    private String finalRenterName;
    /**
     * ctime
     */
    private Long ctime;
    /**
     * utime
     */
    private Long utime;


}
