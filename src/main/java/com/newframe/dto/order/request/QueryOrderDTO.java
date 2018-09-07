package com.newframe.dto.order.request;

import lombok.Data;

import java.util.List;

/**
 * @author kfm
 */
@Data
public class QueryOrderDTO {
    /** 订单状态 */
    private Integer orderStatus;
    /**排序，1：按时间从近到远，2：按时间从远到近*/
    private Integer sort;
    private Integer pageSize;
    private Integer currentPage;
}
