package com.newframe.dto.block;

import lombok.Data;

/**
 * 出租方发货
 * @author WangBin
 */
@Data
public class LessorDeliver extends ExpressInfo {

    /**
     * 订单id
     */
    private Long orderNum;
    /**
     * 出租方uid
     */
    private Long lessorUid;
}
