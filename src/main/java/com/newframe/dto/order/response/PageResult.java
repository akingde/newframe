package com.newframe.dto.order.response;

import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * 分页数据包装
 * @author kfm
 * @date 2018.08.29 15:14
 */
@Data
public class PageResult {
    private Long total;

    private Object list;

    public void setPage(Page page){
        this.list = page.getContent();
        this.total = page.getTotalElements();
    }
}
