package com.photo.photo_backend.utils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PicUtil {
    // 压缩图片
    public static void compressPic(String srcFilePath, String descFilePath, double ratio) {
        File srcFile = new File(srcFilePath);
        long srcFileSize = srcFile.length();
        // 图片小于100kb不压缩
        if (srcFileSize < 100 * 1024) {
            ratio = 1;
        }else if (srcFileSize < 500 * 1024) {
            ratio = 0.8;
        }else if (srcFileSize < 1024 * 1024) {
            ratio = 0.6;
        }else if (srcFileSize < 2 * 1024 * 1024) {
            ratio = 0.4;
        }else if (srcFileSize < 5 * 1024 * 1024) {
            ratio = 0.3;
        }else {
            ratio = 0.2;
        }
        try {
            BufferedImage src = ImageIO.read(srcFile);
            int width = src.getWidth();
            int height = src.getHeight();
            BufferedImage tag = new BufferedImage((int) (width * ratio), (int) (height * ratio), BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(src, 0, 0, (int) (width * ratio), (int) (height * ratio), null);
            ImageIO.write(tag, "JPEG", new File(descFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
