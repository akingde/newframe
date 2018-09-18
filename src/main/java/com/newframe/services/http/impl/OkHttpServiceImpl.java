package com.newframe.services.http.impl;

import com.mzlion.easyokhttp.HttpClient;
import com.mzlion.easyokhttp.request.PostRequest;
import com.newframe.blockchain.entity.ResponseBean;
import com.newframe.resp.block.BlockAddress;
import com.newframe.resp.face.FaceIdentityResp;
import com.newframe.resp.file.CommonResp;
import com.newframe.resp.file.UploadFilesResp;
import com.newframe.services.http.OkHttpService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author:wangdong
 * @description:okHttp的实现类
 */
@Service
public class OkHttpServiceImpl implements OkHttpService {

    /**
     * 传送文件到服务器去
     *
     * @param url    服务器地址
     * @param bucket 阿里云的bucket
     * @param filePath  文件的集合
     * @return
     */
    @Override
    public CommonResp uploadFile(String url, String bucket, String filePath) {
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(bucket) || null == filePath){
            return null;
        }

        CommonResp commonResp = HttpClient.post(url)
                .param("bucket",bucket)
                .param("file",new File(filePath))
                .asBean(CommonResp.class);

        return commonResp;
    }

    /**
     * 传输文件流
     *
     * @param uploadfileUrl
     * @param bucket
     * @param inputStream
     * @param filename
     * @return
     */
    @Override
    public CommonResp uploadFileStream(String uploadfileUrl, String bucket, InputStream inputStream, String filename) {
        if (StringUtils.isEmpty(uploadfileUrl) || StringUtils.isEmpty(bucket) || null == inputStream || StringUtils.isEmpty(filename)){
            return null;
        }
        CommonResp commonResp = HttpClient.post(uploadfileUrl)
                .param("bucket",bucket)
                .param("inputStream",inputStream,filename)
                .asBean(CommonResp.class);

        return commonResp;

    }

    /**
     * 传输两个文件
     * 以流的方式
     * @param uploadfileUrl
     * @param bucket
     * @param inputStream1
     * @param fileName1
     * @param inputStream2
     * @param fileName2
     * @return
     */
    @Override
    public UploadFilesResp uploadTwoFileStream(String uploadfileUrl, String bucket, InputStream inputStream1, String fileName1, InputStream inputStream2, String fileName2) {
        if (StringUtils.isEmpty(uploadfileUrl) || StringUtils.isEmpty(bucket) || null == inputStream1 || StringUtils.isEmpty(fileName1) || null == inputStream2 || StringUtils.isEmpty(fileName2)){
            return null;
        }
        UploadFilesResp uploadFilesResp = HttpClient.post(uploadfileUrl)
                                                    .param("bucket", bucket)
                                                    .param("inputStream1", inputStream1, fileName1)
                                                    .param("inputStream2", inputStream2, fileName2)
                                                    .asBean(UploadFilesResp.class);
        return uploadFilesResp;
    }

    /**
     * 多文件传输
     *
     * @param uploadfileUrl
     * @param bucket
     * @param files
     * @return
     */
    @Override
    public UploadFilesResp uploadFilesStream(String uploadfileUrl, String bucket, List<MultipartFile> files) throws IOException {
        if (StringUtils.isEmpty(uploadfileUrl) || StringUtils.isEmpty(bucket) || CollectionUtils.isEmpty(files)){
            return null;
        }
        PostRequest postRequest = HttpClient.post(uploadfileUrl)
                                                    .param("bucket",bucket);
        for (MultipartFile file : files) {
            postRequest.param("inputStreams",file.getInputStream(),file.getOriginalFilename());
        }
        UploadFilesResp uploadFilesResp = postRequest
                .connectTimeout(300)
                .readTimeout(300)
                .writeTimeout(300)
                .asBean(UploadFilesResp.class);


        return uploadFilesResp;
    }

    /**
     * 身份认证
     *
     * @param faceIdentityUrl
     * @param frontFile
     * @param handFile
     * @param cardNum
     * @param realName
     * @return
     */
    @Override
    public FaceIdentityResp faceIdentity(String faceIdentityUrl, MultipartFile frontFile, MultipartFile handFile, String cardNum, String realName) throws IOException {
        if (StringUtils.isEmpty(faceIdentityUrl) || null == handFile ||null == frontFile || StringUtils.isEmpty(realName) || StringUtils.isEmpty(cardNum)){
            return null;
        }
        FaceIdentityResp faceIdentityResp  = HttpClient.post(faceIdentityUrl)
                .param("name", realName)
                .param("codeno", cardNum)
                .param("image_best",frontFile.getInputStream(),frontFile.getOriginalFilename())
                .param("image_env",handFile.getInputStream(),handFile.getOriginalFilename())
                .connectTimeout(300)
                .readTimeout(300)
                .writeTimeout(300)
                .asBean(FaceIdentityResp.class);

        return faceIdentityResp;
    }

    /**
     * 一次就只认证正面照
     *
     * @param faceIdentityUrl
     * @param frontFile
     * @return
     */
    @Override
    public FaceIdentityResp faceFront(String faceIdentityUrl, MultipartFile frontFile) throws IOException {
        if (StringUtils.isEmpty(faceIdentityUrl) || null == frontFile){
            return null;
        }
        FaceIdentityResp faceIdentityResp  = HttpClient.post(faceIdentityUrl)
                .param("image_best",frontFile.getInputStream(),frontFile.getOriginalFilename())
                .connectTimeout(300)
                .readTimeout(300)
                .writeTimeout(300)
                .asBean(FaceIdentityResp.class);

        return faceIdentityResp;
    }

    /**
     * 校验手持身份证、名字和身份证号
     *
     *
     * @param faceHandUrl
     * @param handFile
     * @param realName
     * @param cardNum
     * @param platformtoken
     * @return
     */
    @Override
    public FaceIdentityResp faceHand(String faceHandUrl, MultipartFile handFile, String realName, String cardNum, String platformtoken) throws IOException {
        if (StringUtils.isEmpty(faceHandUrl) || StringUtils.isEmpty(platformtoken) || null == handFile || StringUtils.isEmpty(realName) || StringUtils.isEmpty(cardNum)){
            return null;
        }
        FaceIdentityResp faceIdentityResp  = HttpClient.post(faceHandUrl)
                .param("platformtoken",platformtoken)
                .param("image_env",handFile.getInputStream(),handFile.getOriginalFilename())
                .param("name", realName)
                .param("codeno", cardNum)
                .connectTimeout(300)
                .readTimeout(300)
                .writeTimeout(300)
                .asBean(FaceIdentityResp.class);

        return faceIdentityResp;
    }

    /**
     * 根据Uid去获取地址
     *
     * @param apiAddressService
     * @param jsonBody
     * @return
     */
    @Override
    public BlockAddress getBlockAddress(String apiAddressService, String jsonBody) {
        if (StringUtils.isEmpty(apiAddressService) || StringUtils.isEmpty(jsonBody)){
            return null;
        }
        String addressHeader = "Basic MzNleGNoYW5nZTpeZXhjaGFuZ2VAMjAxOEBidHl0b2tlbkAzM15g";

        BlockAddress blockAddress = HttpClient.textBody(apiAddressService)
                .header("authorization",addressHeader)
                .json(jsonBody)
                .charset("utf-8")
                .asBean(BlockAddress.class);

        return blockAddress;
    }

    /**
     * 发送区块链
     *
     * @param blockUrl
     * @param json
     * @return
     */
    @Override
    public ResponseBean<String> sendBlockChain(String blockUrl, String json) {
        ResponseBean<String> result = HttpClient.textBody(blockUrl)
                .json(json)
                .charset("utf-8")
                .asBean(new ResponseBean<String>().getClass());
        return null;
    }
}
