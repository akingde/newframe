package com.newframe.dto.order;

import lombok.Data;

/**
 * 资金方查询订单数据封装
 * @author kfm
 */
@Data
public class FunderQueryOrderDto extends QueryOrderDto {
    private Long renterId;
}

