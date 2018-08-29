package com.newframe.controllers;

import com.newframe.enums.CodeStatus;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页参数封装
 *
 * @author kfm
 */

public class PageJsonResult extends JsonResult {
    /**
     * 设置分页信息
     *
     * @param page
     */
    public PageJsonResult setPage(Page page) {
        JsonPage jsonPage = new JsonPage();
        jsonPage.setTotal(page.getTotalElements());
        jsonPage.setData(page.getContent());
        setData(jsonPage);
        return this;
    }


    /**
     * List不需要转化
     *
     * @param codeStatus
     * @param page
     */
    public PageJsonResult(CodeStatus codeStatus, Page page) {
        super(codeStatus);
        setPage(page);
    }

    /**
     * List需要转化
     *
     * @param codeStatus
     * @param data
     * @param total
     */
    public PageJsonResult(CodeStatus codeStatus, List data, Long total) {
        super(codeStatus);
        JsonPage jsonPage = new JsonPage();
        jsonPage.setData(data);
        jsonPage.setTotal(total);
        setData(jsonPage);
    }


    @Data
    class JsonPage {
        private Long total;
        private List data;
    }
}
