package com.newframe.dto.after.request;

import com.newframe.dto.user.request.PageSearchDTO;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class DrawAssetSearchDTO extends PageSearchDTO {

    private Long orderId;
    private Integer orderStatus;
    private String merchantName;
    private String phoneNumber;
    private Integer startTime;
    private Integer endTime;
}
