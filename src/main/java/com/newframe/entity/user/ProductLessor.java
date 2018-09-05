package com.newframe.entity.user;

import com.newframe.dto.user.request.ProductModifyDTO;
import com.newframe.utils.BigDecimalUtils;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table product_lessor
 *
 * @mbggenerated do_not_delete_during_merge
 */
@Data
@Entity
@Table(name = "product_lessor")
public class ProductLessor {
    /**
     * id
     * id
     */
    @Id
    @Column(name = "Id")
    private Long id;

    /**
     * 供应商id
     * supplier_id
     */
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 品牌
     * brand
     */
    @Column(name = "brand")
    private String brand;

    /**
     * 型号
     * model
     */
    @Column(name = "model")
    private String model;

    /**
     * 规格，容量
     * specification
     */
    @Column(name = "specification")
    private String specification;

    /**
     * 颜色
     * color
     */
    @Column(name = "color")
    private String color;

    /**
     * 市场指导价
     * guide_price
     */
    @Column(name = "guide_price")
    private BigDecimal guidePrice;

    /**
     * 供应价
     * supply_price
     */
    @Column(name = "supply_price")
    private BigDecimal supplyPrice;

    /**
     * 剩余库存
     * surplus_stock
     */
    @Column(name = "surplus_stock")
    private Integer surplusStock;

    /**
     * 冻结库存
     * frozen_stock
     */
    @Column(name = "frozen_stock")
    private Integer frozenStock;

    /**
     * ctime
     * ctime
     */
    @Column(name = "ctime")
    private Integer ctime;

    /**
     * 碎屏险
     * broken_screen_risks
     */
    @Column(name = "broken_screen_risks")
    private BigDecimal brokenScreenRisks;

    /**
     * utime
     * utime
     */
    @Column(name = "utime")
    private Integer utime;

    /**
     * 删除标记（1：删除，0：未删除，默认0）
     * is_delated
     */
    @Column(name = "is_delated")
    private Integer isDelated;

    public ProductLessor() {
    }

    public ProductLessor(Long uid, ProductModifyDTO condition) {
        this.id = condition.getProductId();
        this.supplierId = uid;
        this.brand = condition.getBrand();
        this.model = condition.getModel();
        this.specification = condition.getSpecification();
        this.color = condition.getColor();
        this.guidePrice = BigDecimalUtils.compareTo(condition.getGuidePrice()) ? condition.getGuidePrice() : BigDecimal.ZERO;
        this.supplyPrice = BigDecimalUtils.compareTo(condition.getSupplyPrice()) ? condition.getSupplyPrice() : BigDecimal.ZERO;
        this.surplusStock = condition.getSurplusStock() != null && condition.getSurplusStock() > 0 ? condition.getSurplusStock(): 0;
        this.frozenStock = 0;
        this.brokenScreenRisks = BigDecimalUtils.compareTo(condition.getBrokenScreenRisks()) ? condition.getBrokenScreenRisks() : BigDecimal.ZERO;
        this.isDelated = 0;
    }
}