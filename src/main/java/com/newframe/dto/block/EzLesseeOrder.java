package com.newframe.dto.block;

import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class EzLesseeOrder extends EzProdDetail{
    private String orderId;
    private Integer orderTime;
    private String phoneNum;
    private String realName;
}
