package com.newframe.dto.block;

import lombok.Data;
import types.EzTransfer;

import java.math.BigDecimal;

/**
 * 租赁商向出租方租机
 * @author WangBin
 */
@Data
public class RentalApply extends LesseeOrder{
    /**
     * 订单id
     */
    private Long orderNum;
    /**
     * 出租方uid
     */
    private Long lessorUid;
    /**
     * 租赁商uid
     */
    private Long merchantUid;
    /**
     * 支付方式   PM_LUMP_SUM 全额      PM_INSTALLMENT 分期
     */
    private EzTransfer.EzPaymentMethod paymentMethod;
    /**
     * 租金
     */
    private BigDecimal rentalAmount;
    /**
     * 租赁期限
     */
    private Integer rentalTerm;
    /**
     * 申请时间
     */
    private Integer applyTime;
}
