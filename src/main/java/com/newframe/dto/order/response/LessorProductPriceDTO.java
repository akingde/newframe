package com.newframe.dto.order.response;

import lombok.Data;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.01 15:47
 */
@Data
public class LessorProductPriceDTO {
    private Long id;
    private String productBrand;
    private String productName;
    private Integer productStorage;
    private Integer productRandomMemory;
    private String productColor;
    private Integer paymentNumber;
    private BigDecimal rentPrice;
    private BigDecimal accidentBenefit;
    private BigDecimal monthPayment;
    private BigDecimal buyOutPrice;
    private Integer ctime;
    private Integer utime;
}
