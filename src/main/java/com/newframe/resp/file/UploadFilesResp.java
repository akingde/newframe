package com.newframe.resp.file;

import lombok.Data;

import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
@Data
public class UploadFilesResp {

    private String code;

    private List<String> data;

    private String message;
}
