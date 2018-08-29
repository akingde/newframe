package com.newframe.dto.user.response;

import lombok.Data;

import java.util.List;

/**
 * @author WangBin
 */
@Data
public class ProductDTO<T> {
    private Long total;
    private List<T> result;

    public ProductDTO() {
    }

    public ProductDTO(Long total) {
        this.total = total;
    }

    public ProductDTO(Long total, List<T> result) {
        this.total = total;
        this.result = result;
    }
}
