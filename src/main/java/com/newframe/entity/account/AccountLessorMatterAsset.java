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
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;



    /**
     * 产品品牌
     */
    private String productModel;
    /**
     * 产品型号
     */
    private String productBrand;
    /**
     * 产品的颜色
     */
    private String productColour;
    /**
     * 产品的物理内存
     */
    private String productStorage;
    /**
     * 产品的运行内存
     */
    private String productMemory;

    /**
     * 订单的状态。1:正常，2:逾期，3:逾期未催收，4:催收中，5:催收已还机
     */
    private Integer orderStatus;

}
