package com.newframe.services.http;

import com.newframe.dto.SmsResult;
import com.newframe.resp.face.FaceIdentityResp;
import com.newframe.resp.file.CommonResp;
import com.newframe.resp.file.UploadFilesResp;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author:wangdong
 * @description:服务间调用一律使用OKhttp3
 */
public interface OkHttpService {

    /**
     * 传送文件到服务器去
     * 目前只支持单文件上传
     * 除非压缩
     * @param url 服务器地址
     * @param bucket 阿里云的bucket
     * @param filePath 文件
     * @return
     */
    CommonResp uploadFile(String url, String bucket, String filePath);

    /**
     * 传输文件流
     * @param uploadfileUrl
     * @param bucket
     * @param inputStream
     * @param filename
     * @return
     */
    CommonResp uploadFileStream(String uploadfileUrl, String bucket, InputStream inputStream, String filename);

    /**
     * 传输两个文件
     * @param uploadfileUrl
     * @param bucket
     * @param inputStream1
     * @param fileName1
     * @param inputStream2
     * @param fileName2
     * @return
     */
    UploadFilesResp uploadTwoFileStream(String uploadfileUrl, String bucket, InputStream inputStream1, String fileName1, InputStream inputStream2, String fileName2);

    /**
     * 多文件传输
     * @param uploadfileUrl
     * @param bucket
     * @param files
     * @return
     */
    UploadFilesResp uploadFilesStream(String uploadfileUrl, String bucket, List<MultipartFile> files) throws IOException;

    /**
     * 身份认证
     * 一次性认证两张照片、识别名字和身份证号码
     * @param faceIdentityUrl
     * @param frontFile
     * @param handFile
     * @param cardNum
     * @param realName
     * @return
     */
    FaceIdentityResp faceIdentity(String faceIdentityUrl, MultipartFile frontFile, MultipartFile handFile, String cardNum, String realName) throws IOException;

    /**
     * 一次就只认证正面照
     * @param faceIdentityUrl
     * @param frontFile
     * @return
     */
    FaceIdentityResp faceFront(String faceIdentityUrl, MultipartFile frontFile) throws IOException;

    /**
     * 校验手持身份证、名字和身份证号
     *
     * @param faceHandUrl
     * @param handFile
     * @param realName
     * @param cardNum
     * @param platformtoken
     * @return
     */
    FaceIdentityResp faceHand(String faceHandUrl, MultipartFile handFile, String realName, String cardNum, String platformtoken) throws IOException;

    /**
     * 发送验证码
     * @param mobile
     * @param code
     * @return
     */
    SmsResult sendVerificationCode(String mobile, String templateCode, String code);

    /**
     * 发送通知短信
     * @param mobile
     * @param templateCode
     */
    void sendSmallMessage(String mobile,  String templateCode);
}
