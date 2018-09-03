package com.newframe.entity.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.01 15:47
 */
@Data
@Entity
public class LessorProductPrice {

    public static final String PAYMENT_NUMBER="paymentNumber";

    @Id
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
