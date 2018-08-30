package com.newframe.dto.after.request;

import com.newframe.dto.user.request.PageSearchDTO;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class FunderSearchDTO extends PageSearchDTO {

    private String merchantName;
    private String phoneNumber;
    private String legalEntity;
    private String legalEntityIdNumber;
    private Integer startTime;
    private Integer endTime;
}
