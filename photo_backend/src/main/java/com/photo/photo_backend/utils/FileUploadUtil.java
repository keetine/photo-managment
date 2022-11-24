package com.photo.photo_backend.utils;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class FileUploadUtil {
    public String generateFileName(String fileName) {
        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        return UUID.randomUUID().toString().replace("-", "") + "." + suffix;
    }

    // 保存头像
    public String saveAvatar(MultipartFile file) {
        ApplicationHome home = new ApplicationHome(this.getClass());
        String fileName = generateFileName(Objects.requireNonNull(file.getOriginalFilename()));
        File n_file = new File(home.getSource().getParent() + "\\upload\\image\\avatar\\" + fileName);
        if (!(n_file.exists())) {
            n_file.mkdirs();
        }
        try {
            file.transferTo(n_file);
        } catch (Exception e) {
            return null;
        }
        // 压缩头像
        PicUtil.compressPic(n_file.getPath(), n_file.getPath(), 0.3);
        return "api/avatar/" + fileName;
    }

    // 保存相册
    public Map<String, String> savePhoto(MultipartFile file) {
        ApplicationHome home = new ApplicationHome(this.getClass());
        String fileName = generateFileName(Objects.requireNonNull(file.getOriginalFilename()));
        File save_file = new File(home.getSource().getParent() + "\\upload\\image\\photos\\" + fileName);
        File thumbnail_file = new File(home.getSource().getParent() + "\\upload\\image\\thumbnail\\");
        if (!(save_file.exists())) {
            save_file.mkdirs();
        }
        if (!(thumbnail_file.exists())) {
            thumbnail_file.mkdirs();
        }
        try {
            file.transferTo(save_file);
            // 压缩图片
            PicUtil.compressPic(save_file.getPath(), thumbnail_file.getPath()+"\\"+fileName, 0.3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Map.of("url", "api/photos/" + fileName, "thumbnail", "api/thumbnail/" + fileName);
    }
    // 删除照片
    public void deletePhoto(String fileName) {
        // 去掉fileName前面的api/photos/和api/thumbnail/
        if (fileName.startsWith("api/photos/")) {
            fileName = fileName.substring(11);
        } else if (fileName.startsWith("api/thumbnail/")) {
            fileName = fileName.substring(14);
        }
        ApplicationHome home = new ApplicationHome(this.getClass());
        File save_file = new File(home.getSource().getParent() + "\\upload\\image\\photos\\" + fileName);
        File thumbnail_file = new File(home.getSource().getParent() + "\\upload\\image\\thumbnail\\" + fileName);
        if (save_file.exists()) {
            save_file.delete();
        }
        if (thumbnail_file.exists()) {
            thumbnail_file.delete();
        }
    }
}
