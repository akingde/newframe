package com.newframe.dto.block;

import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class LesseeOrder extends ProdDetail {
    private String orderId;
    private Integer orderTime;
    private String phoneNum;
    private String realName;
}
