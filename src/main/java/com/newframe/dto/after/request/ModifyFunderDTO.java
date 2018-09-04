package com.newframe.dto.after.request;

import lombok.Data;

import java.util.List;

/**
 * @author WangBin
 */
@Data
public class ModifyFunderDTO {

    private Long uid;
    private List<Long> funderUids;
}
