package com.newframe.controllers;

import com.newframe.enums.CodeStatus;
import lombok.Data;

/**
 * 分页参数封装
 * @author kfm
 */

public class PageJsonResult extends JsonResult {
    private Long total;
    public PageJsonResult(CodeStatus codeStatus) {
        super(codeStatus);
    }

    public PageJsonResult(CodeStatus codeStatus, Object data) {
        super(codeStatus, data);
    }

    public PageJsonResult(String code, String message) {
        super(code, message);
    }

    public PageJsonResult(CodeStatus codeStatus, Object data, Long total) {
        super(codeStatus, data);
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
