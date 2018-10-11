package com.newframe.dto;

import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class CommonResult {

    private String code;
    private String mesage;
    private Result data;

    @Data
    public static class Result{
        private Boolean status;
        private String msg;
    }
}
