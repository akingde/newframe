package com.newframe.dto.user.response;

import com.newframe.entity.user.ProductSupplier;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author WangBin
 */
@Data
public class ProductSupplierDTO {

    private Long productId;
    private String brand;
    private String model;
    private String color;
    private String ram;
    private String rom;
    private BigDecimal guidePrice;
    private BigDecimal supplyPrice;
    private Integer surplusStock;
    private Integer frozenStock;
    private Integer ctime;
    private Integer utime;
    private Boolean isDelete;

    public ProductSupplierDTO() {
    }

    public ProductSupplierDTO(ProductSupplier productSupplier) {
        this.productId = productSupplier.getId();
        this.brand = productSupplier.getBrand();
        this.model = productSupplier.getModel();
        this.color = productSupplier.getColor();
        this.ram = productSupplier.getRam();
        this.rom = productSupplier.getRom();
        this.guidePrice = productSupplier.getGuidePrice();
        this.supplyPrice = productSupplier.getSupplyPrice();
        this.surplusStock = productSupplier.getSurplusStock();
        this.frozenStock = productSupplier.getFrozenStock();
        this.ctime = productSupplier.getCtime().intValue();
        this.utime = productSupplier.getUtime().intValue();
        this.isDelete = productSupplier.getIsDelated().equals(1);
    }
}
