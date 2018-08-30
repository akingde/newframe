package com.newframe.dto.order.response;

import lombok.Data;

/**
 * 查询快递信息数据封装
 * @author kfm
 * @date 2018.08.30 17:19
 */
@Data
public class DeliverDTO {
    private String serialNumber;
    private String expressCompany;
    private String expressNumber;
    private Long expressTime;
    private String deliverInfo;
}
