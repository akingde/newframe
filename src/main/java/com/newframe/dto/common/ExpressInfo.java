package com.newframe.dto.common;

import lombok.Data;

/**
 * @author:wangdong
 * @description:
 */
@Data
public class ExpressInfo {

    /**
     * 快递公司
     */
    private String expCompany;

    /**
     * 快递单号
     */
    private String expNo;

    /**
     * 快递的状态
     */
    private String expStatus;
}
