package com.newframe.dto.block;

import lombok.Data;
import types.EzTransfer;

import java.math.BigDecimal;

/**
 * @author WangBin
 */
@Data
public class RentalApply extends LesseeOrder{
    private Long orderNum;
    private Long lessorUid;
    private Long merchantUid;
    private EzTransfer.EzPaymentMethod paymentMethod;
    private BigDecimal rentalAmount;
    private Integer rentalTerm;
    private Integer applyTime;
}
