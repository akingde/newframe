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
        if(files == null || files.length == 0)
            return false;
        for (MultipartFile file : files) {
            try {
                BufferedImage image = ImageIO.read(file.getInputStream());
                if(image == null){
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
