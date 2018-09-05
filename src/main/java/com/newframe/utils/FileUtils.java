package com.newframe.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author WangBin
 */
public class FileUtils {

    /**
     * 判断文件是否是图片和超过了图片个数以及是否为空
     * @param length
     * @param files
     * @return
     */
    public static boolean checkImageAndEmpty(Integer length, List<MultipartFile> files){
        if(CollectionUtils.isEmpty(files)){
            return true;
        }
        if(checkLength(length, files)){
            return true;
        }
        return !checkImageAndEmpty(files);
    }

    /**
     *  校验多个图片是否是null和图片格式
     * @param files
     * @return
     */
    public static boolean checkImageAndEmpty(List<MultipartFile> files){
        if(CollectionUtils.isEmpty(files)){
            return false;
        }
        for (MultipartFile file : files) {
            if (file == null && files.size() == 1){
                return false;
            }
            if(file == null){
                continue;
            }
            if(!checkImage(file)){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个文件数组是否是图片和长度   不包含判断空
     * @param files
     * @return
     */
    public static boolean checkImage(Integer length, List<MultipartFile> files){

        if (CollectionUtils.isEmpty(files)){
            return true;
        }
        if (length != null){
            if (files.size() > length){
                return false;
            }
        }
        for (MultipartFile file : files){
            if(file == null){
                continue;
            }
            if(!checkImage(file)){
                return false;
            }
        }

        return true;
    }

    /**
     * 判断文件是否是图片
     * @param file
     * @return
     */
    private static boolean checkImage(MultipartFile file){
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断文件是否是空和是否超过了指定个数
     * @param length
     * @param files
     * @return
     */
    private static boolean checkLength(Integer length, List<MultipartFile> files){
        return length != null ? files.size() > length : true;
    }

    /**
     * 检查所有的url是否为空，长度是否超过，并且是否合法
     * @param length
     * @param urls
     * @return
     */
    public static boolean isAllCorrect(Integer length, List<String> urls){
        if (CollectionUtils.isEmpty(urls)){
            return false;
        }
        if(length != null) {
            if (urls.size() > length) {
                return false;
            }
        }
        for (String url : urls) {
            if(StringUtils.isNotEmpty(url) || url.split(",").length > 0){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<String> s = Lists.newArrayList();
        System.out.println(StringUtils.join(s, ","));
    }
}