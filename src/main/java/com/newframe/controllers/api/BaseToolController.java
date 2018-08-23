package com.newframe.controllers.api;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.services.common.AliossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 阿里云的处理文件的接口
 */
@RestController
@RequestMapping("/api/file/")
public class BaseToolController extends BaseController {

    @Autowired
    private AliossService aliossService;

    /**
     * 这个仅为测试文件上传到服务器用
     * @param multipartFile
     * @return
     */
    @RequestMapping("uploadToServer")
    public JsonResult uploadToServer(@RequestPart("file") MultipartFile multipartFile){

        String result = aliossService.uploadToServer(multipartFile);

        return success(result);
    }

    /**
     * 上传单个文件到阿里云
     * 以File的文件的形式
     * @param file
     * @param bucket
     * @return
     * @throws IOException
     */
    @RequestMapping("uploadFileToBasetool")
    public JsonResult uploadFileToBasetool(@RequestPart("file") MultipartFile file, String bucket) throws IOException {

        String result = aliossService.uploadFileToBasetool(file,bucket);

        return success(result);
    }


    /**
     * 上传单个文件到阿里云
     * 以流的方式传输数据
     * @param file
     * @param bucket
     * @return
     * @throws IOException
     */
    @RequestMapping("uploadFileStreamToBasetool")
    public JsonResult uploadFileStreamToBasetool(@RequestPart("file") MultipartFile file, String bucket) throws IOException {

        String result = aliossService.uploadFileStreamToBasetool(file,bucket);

        return success(result);
    }

    /**
     * 一次传两个文件
     * @param file1
     * @param file2
     * @param bucket
     * @return
     * @throws IOException
     */
    @RequestMapping("uploadTwoFileStreamToBasetool")
    public JsonResult uploadTwoFileStreamToBasetool(@RequestPart("file1") MultipartFile file1, @RequestPart("file2") MultipartFile file2, String bucket) throws IOException {

        List<String> result = aliossService.uploadTwoFileStreamToBasetool(file1, file2 ,bucket);

        return success(result);
    }

    /**
     * 一次性传输多个文件
     * @param files
     * @param bucket
     * @return
     * @throws IOException
     */
    @RequestMapping("uploadFilesToBasetool")
    public JsonResult uploadFilesToBasetool(@RequestPart("files") List<MultipartFile> files, String bucket) throws IOException {

        List<String> result = aliossService.uploadFilesToBasetool(files ,bucket);

        return success(result);
    }
}
