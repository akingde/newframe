package com.newframe.dto.user.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author WangBin
 */
@Data
public class ProductModifyDTO {

    private Long productId;
    private String brand;
    private String model;
    private String color;
    private Integer ram;
    private Integer rom;
    private BigDecimal guidePrice;
    private BigDecimal supplyPrice;
    private Integer surplusStock;
    private BigDecimal brokenScreenRisks;
}
