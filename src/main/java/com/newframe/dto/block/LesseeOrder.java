package com.newframe.dto.block;

import lombok.Data;

/**
 * 第三方订单信息
 * @author WangBin
 */
@Data
public class LesseeOrder extends ProdDetail {

    /**
     * 第三方订单id
     */
    private Long orderId;
    /**
     * 第三方订单推过来的时间
     */
    private Integer orderTime;
    /**
     * 租客手机号
     */
    private String phoneNum;
    /**
     * 租客姓名
     */
    private String realName;
    /**
     * 租客身份证号
     */
    private String idNum;
}
