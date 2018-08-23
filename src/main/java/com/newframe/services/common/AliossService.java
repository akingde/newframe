package com.newframe.services.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author:wangdong
 * @description:文件上传的接口
 */
public interface AliossService {

    /**
     * 传文件到服务器
     * @param file
     * @return
     */
    String uploadToServer(MultipartFile file);

    /**
     * 不要用这个
     * 将文件传输给Base服务
     * @param multipartFile
     * @param bucket
     * @return
     */
    String uploadFileToBasetool(MultipartFile multipartFile, String bucket) throws IOException;

    /**
     * 文件流传输
     * @param file
     * @param bucket
     * @return
     */
    String uploadFileStreamToBasetool(MultipartFile file, String bucket) throws IOException;

    /**
     * 传输两张图片
     * @param file1
     * @param file2
     * @param bucket
     * @return
     */
    List<String> uploadTwoFileStreamToBasetool(MultipartFile file1, MultipartFile file2, String bucket) throws IOException;

    /**
     * 上传多张图片
     * @param files
     * @param bucket
     * @return
     */
    List<String> uploadFilesToBasetool(List<MultipartFile> files, String bucket) throws IOException;
}
