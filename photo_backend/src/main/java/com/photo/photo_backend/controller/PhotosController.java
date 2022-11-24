package com.photo.photo_backend.controller;

import com.photo.photo_backend.annotations.CheckToken;
import com.photo.photo_backend.service.IPhotosService;
import com.photo.photo_backend.utils.AjaxResult;
import com.photo.photo_backend.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/photos")
public class PhotosController {

    private IPhotosService service;

    @Autowired
    public PhotosController(IPhotosService service) {
        this.service = service;
    }

    // 上传图片
    @CheckToken
    @PostMapping(value = "/upload")
    // 上传多个文件
    public AjaxResult upload(@RequestParam MultipartFile[] files, @RequestParam String username, @RequestParam String address, @RequestParam Integer categoryId) {
        return new AjaxResult().success(service.upload(username, address, categoryId, files));
    }
    // 获取所有照片数量
    @CheckToken
    @GetMapping("/getPhotosCount")
    public AjaxResult getPhotosCount(HttpServletRequest request) {
        String username = TokenUtil.getUsername(request.getHeader("Authorization"));
        return new AjaxResult().success(service.getPhotosCount(username));
    }
    //获取指定指定范围的分类照片
    @CheckToken
    @GetMapping("/getPhotosByRange")
    public AjaxResult getPhotosByRange( @RequestParam Integer start, @RequestParam Integer end, @RequestParam Integer categoryId) {
        return new AjaxResult().success(service.getPhotosByRange(start, end,categoryId));
    }

    //获取指定指定范围的所有照片
    @CheckToken
    @GetMapping("/getAllPhotosByRange")
    public AjaxResult getPhotosByRange( @RequestParam Integer start, @RequestParam Integer end,HttpServletRequest request) {
        String username = TokenUtil.getUsername(request.getHeader("Authorization"));
        return new AjaxResult().success(service.getPhotosByRange(start, end,username));
    }
    // 首页所有图片
    @CheckToken
    @GetMapping("/getAllPhotos")
    public AjaxResult getAllPhotos(HttpServletRequest request) {
        String username = TokenUtil.getUsername(request.getHeader("Authorization"));
        return new AjaxResult().success(service.getAllPhotos(username));
    }
    // 删除
    @CheckToken
    @DeleteMapping("/delete")
    public AjaxResult delete(@RequestBody List<Integer> ids) {
        return new AjaxResult().success(service.delete(ids));
    }

    // 根据分类id获取分类名称
    @CheckToken
    @GetMapping("/getCategoryNameById/{id}")
    public AjaxResult getCategoryNameById(@PathVariable Integer id) {
        return new AjaxResult().success(service.getCategoryNameById(id));
    }
}
