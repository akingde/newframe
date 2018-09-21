package com.newframe.dto.block;

import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class LessorDeliver extends ExpressInfo {

    private Long orderNum;
    private Long lessorUid;
}
