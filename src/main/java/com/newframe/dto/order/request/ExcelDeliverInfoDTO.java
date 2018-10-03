package com.newframe.dto.order.request;

import com.newframe.utils.excel.ExcelVOAttribute;
import lombok.Data;

/**
 * @author kfm
 * @date 2018-08-10
 * 发货信息封装
 */
@Data
public class ExcelDeliverInfoDTO {
    /**订单编号*/
    @ExcelVOAttribute(name = "订单id",column = "A")
    private Long orderId;

    /**手机序列号*/
    @ExcelVOAttribute(name = "手机序列号",column = "B")
    private String serialNumber;

    /**快递单号*/
    @ExcelVOAttribute(name = "快递单号",column = "C")
    private String deliverId;

    /**发货时间*/
    @ExcelVOAttribute(name = "发货时间",column = "D")
    private String deliverTime;

    /**快递公司*/
    @ExcelVOAttribute(name = "快递公司",column = "E")
    private String expressName;
}
