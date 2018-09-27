package com.newframe.dto.block;

import lombok.Data;

/**
 * 物流信息
 * @author WangBin
 */
@Data
public class ExpressInfo {

    /**
     * 物流编号
     */
    private String trackingNum;
    /**
     * 手机序列号
     */
    private String iMEI;
    /**
     * 快递公司名
     */
    private String expressCoName;
    /**
     * 发货时间
     */
    private Integer deliveryTime;
    /**
     * 确认收货时间   暂不传递
     */
    private Integer confirmTime;
}
