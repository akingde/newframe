package com.newframe.dto.block;

import lombok.Data;

/**
 * 租客租赁产品详情
 * @author WangBin
 */
@Data
public class ProdDetail {
    /**
     * 产品品牌
     */
    private String prodBrand;
    /**
     * 产品名称
     */
    private String prodName;
    /**
     * 产品颜色
     */
    private String prodColor;
    /**
     * 存储容量
     */
    private String prodStorage;
    /**
     * 租期
     */
    private Integer leaseTerm;
    /**
     * 数量
     */
    private Integer prodAmount;
}
