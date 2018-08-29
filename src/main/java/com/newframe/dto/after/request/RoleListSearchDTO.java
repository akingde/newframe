package com.newframe.dto.after.request;

import com.newframe.dto.user.request.PageSearchDTO;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WangBin
 */
@Data
public class RoleListSearchDTO extends PageSearchDTO {

    private Integer roleType;
    private String merchantName;
    private String legalEntity;
    private String phoneNumber;
    private Integer roleStatus;
    private Integer startTime;
    private Integer endTime;

    public String getMerchantName() {
        return StringUtils.isEmpty(merchantName) ? "%" + merchantName + "%" : null;
    }
}
