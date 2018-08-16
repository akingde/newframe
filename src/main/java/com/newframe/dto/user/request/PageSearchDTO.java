package com.newframe.dto.user.request;

import lombok.Data;
import lombok.Setter;

/**
 *  分页查询
 * @author WangBin
 */
@Setter
public class PageSearchDTO {

    private Integer currentPage;
    private Integer pageSize;

    public Integer getCurrentPage() {
        return currentPage == null || currentPage < 1 ? 1 : currentPage;
    }

    public Integer getPageSize() {

        return pageSize == null || pageSize < 1 ? 10 : pageSize;
    }
}
