package com.newframe.dto.block;

import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class ExpressInfo {

    private String trackingNum;
    private String iMEI;
    private String expressCoName;
    private Integer deliveryTime;
    private Integer confirmTime;
}
