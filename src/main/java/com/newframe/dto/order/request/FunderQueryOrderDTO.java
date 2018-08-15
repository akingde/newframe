package com.newframe.dto.order.request;

import lombok.Data;

/**
 * 资金方查询订单数据封装
 * @author kfm
 */
@Data
public class FunderQueryOrderDTO extends QueryOrderDTO {
    private String renterName;
}

