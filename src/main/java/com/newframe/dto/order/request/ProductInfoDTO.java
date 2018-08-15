package com.newframe.dto.order.request;

import lombok.Data;

@Data
public class ProductInfoDTO {
    private String productBrand;
    private String productName;
    private Integer productStorage;
    private Integer productRandomMemory;
    private String productColor;
}
