package com.newframe.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author WangBin
 */
public class FileUtils {

    public static boolean checkImage(MultipartFile ... files){
        if(isEmpty(files)){
            return false;
        }
        for (MultipartFile file : files) {
            if(!checkImage(file)){
                return false;
            }
        }
        return true;
    }

    public static boolean checkImage(MultipartFile[] ... files){
        for (MultipartFile[] file : files) {
            for (MultipartFile multipartFile : file){
                if(!checkImage(multipartFile)){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEmpty(MultipartFile ... files){
        if(files == null || files.length == 0)
            return true;
        return false;
    }

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
}
