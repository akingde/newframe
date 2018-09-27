package com.newframe.dto.user.response;

import com.google.common.collect.Lists;
import com.newframe.entity.user.ProductLessor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author WangBin
 */
@Data
public class ProductHirerDTO {
    private Long productId;
    private String brand;
    private String model;
    private String color;
    private String ram;
    private String rom;
    private BigDecimal guidePrice;
    private BigDecimal supplyPrice;
    private BigDecimal brokenScreenRisks;
    private Integer surplusStock;
    private Integer frozenStock;
    private Integer ctime;
    private Integer utime;
    private Boolean isDelete;

    public ProductHirerDTO() {
    }

    public ProductHirerDTO(ProductLessor productLessor) {
        this.productId = productLessor.getId();
        this.brand = productLessor.getBrand();
        this.model = productLessor.getModel();
        this.color = productLessor.getColor();
        this.ram = productLessor.getRam();
        this.rom = productLessor.getRom();
        this.guidePrice = productLessor.getGuidePrice();
        this.supplyPrice = productLessor.getSupplyPrice();
        this.brokenScreenRisks = productLessor.getBrokenScreenRisks();
        this.surplusStock = productLessor.getSurplusStock();
        this.frozenStock = productLessor.getFrozenStock();
        this.ctime = productLessor.getCtime().intValue();
        this.utime = productLessor.getUtime().intValue();
        this.isDelete = productLessor.getIsDelated().equals(1);
    }
}
