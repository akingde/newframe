package com.newframe.services.common.impl;

import com.newframe.enums.SystemCode;
import com.newframe.resp.file.CommonResp;
import com.newframe.resp.file.UploadFilesResp;
import com.newframe.services.common.AliossService;
import com.newframe.services.http.OkHttpService;
import com.newframe.utils.log.GwsLogger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author:wangdong
 * @description:阿里云上传文件的实现类
 */
@Configuration
@Service
public class AliossServiceImpl implements AliossService {

    @Value("${file.folder}")
    private String fileFolder;

    @Autowired
    private OkHttpService okHttpService;

    @Value("${api.besetool.service}/api/")
    private String apiBasetoolService;

    /**
     * 传文件到服务器
     *
     * @param multipartFile
     * @return
     */
    @Override
    public String uploadToServer(MultipartFile multipartFile) {
        if (null == multipartFile){
            return null;
        }

        StringBuilder key = new StringBuilder();
        String fileName = multipartFile.getOriginalFilename();
        String postfix = getPostfix(fileName);
        /*if (!org.springframework.util.StringUtils.isEmpty(postfix)) {
            key.append(postfix).append("/");
        }*/
        key.append(UUID.randomUUID().toString());
        if (!org.springframework.util.StringUtils.isEmpty(postfix)) {
            key.append(".").append(postfix);
        }
        String fixKey = String.valueOf(key);

        String saveAsPath = fileFolder+fixKey;

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File file = new File(saveAsPath);
            if (file.exists()) {
                GwsLogger.info("file {} already exist", saveAsPath);
                file.delete();
            }
            file.createNewFile();

            inputStream = multipartFile.getInputStream();
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            int bufSize = 1024 * 4;
            byte[] buffer = new byte[bufSize];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return saveAsPath;
        } catch (Exception e) {
            GwsLogger.error(e, "download {} error");
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 这个比较愚蠢
     * 不要用这个
     * 这个是将文件通过浏览器上传到服务器，再通过浏览器传到阿里云
     * 将文件传输给Base服务
     * @param multipartFile
     * @param bucket
     * @return
     */
    @Override
    public String uploadFileToBasetool(MultipartFile multipartFile, String bucket) throws IOException {
        if (null == multipartFile || StringUtils.isEmpty(bucket)){
            return null;
        }
        //
        String filePath = uploadToServer(multipartFile);
        String uploadfileUrl = apiBasetoolService + "file/uploadFile";
        CommonResp commonResp = okHttpService.uploadFile(uploadfileUrl,bucket,filePath);
        if (null == commonResp || !commonResp.getCode().equals(SystemCode.SUCCESS.getCode())){
            return null;
        }
        return commonResp.getData();
    }

    /**
     * 文件流传输
     *
     * @param file
     * @param bucket
     * @return
     */
    @Override
    public String uploadFileStreamToBasetool(MultipartFile file, String bucket) throws IOException {
        if (null == file || StringUtils.isEmpty(bucket)){
            return null;
        }
        String filename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        String uploadfileUrl = apiBasetoolService + "file/uploadFileStream";
        CommonResp commonResp = okHttpService.uploadFileStream(uploadfileUrl,bucket,inputStream,filename);
        if (null == commonResp || !commonResp.getCode().equals(SystemCode.SUCCESS.getCode())){
            return null;
        }
        return commonResp.getData();
    }

    /**
     * 传输两个文件到服务器
     * @param file1
     * @param file2
     * @param bucket
     * @return
     */
    @Override
    public List<String> uploadTwoFileStreamToBasetool(MultipartFile file1, MultipartFile file2, String bucket) throws IOException {
        if (null == file1 || null == file2 || StringUtils.isEmpty(bucket)){
            return Collections.EMPTY_LIST;
        }
        String fileName1 = file1.getOriginalFilename();
        InputStream inputStream1 = file1.getInputStream();

        String fileName2 = file2.getOriginalFilename();
        InputStream inputStream2 = file2.getInputStream();

        String uploadfileUrl = apiBasetoolService + "file/uploadTwoFileStream";
        UploadFilesResp uploadFilesResp = okHttpService.uploadTwoFileStream(uploadfileUrl,bucket,inputStream1,fileName1,inputStream2,fileName2);
        if (null == uploadFilesResp || !uploadFilesResp.getCode().equals(SystemCode.SUCCESS.getCode())){
            return Collections.EMPTY_LIST;
        }
        return uploadFilesResp.getData();
    }

    /**
     * 上传多张图片
     *
     * @param files
     * @param bucket
     * @return
     */
    @Override
    public List<String> uploadFilesToBasetool(List<MultipartFile> files, String bucket) throws IOException {
        if (CollectionUtils.isEmpty(files) || StringUtils.isEmpty(bucket)){
            return Collections.EMPTY_LIST;
        }


        String uploadfileUrl = apiBasetoolService + "file/uploadFilesStream";
        UploadFilesResp uploadFilesResp = okHttpService.uploadFilesStream(uploadfileUrl, bucket, files);
        if (null == uploadFilesResp || !uploadFilesResp.getCode().equals(SystemCode.SUCCESS.getCode())){
            return Collections.EMPTY_LIST;
        }
        return uploadFilesResp.getData();
    }


    private String getPostfix (String file){
        if (null == file) {
            return "";
        }
        int postfixIdx = file.lastIndexOf(".");
        if (-1 == postfixIdx) {
            return "";
        } else {
            return file.substring(postfixIdx + 1);
        }
    }
}
