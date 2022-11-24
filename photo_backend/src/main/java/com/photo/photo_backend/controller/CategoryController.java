package com.photo.photo_backend.controller;

import com.photo.photo_backend.annotations.CheckToken;
import com.photo.photo_backend.service.ICategoryService;
import com.photo.photo_backend.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private ICategoryService service;

    @Autowired
    public void setService(ICategoryService service) {
        this.service = service;
    }

    @CheckToken
    @GetMapping("/getCategories")
    public AjaxResult getCategories(@RequestParam String username) {
        return new AjaxResult().success(service.getCategories(username));
    }
    //addCategory
    @CheckToken
    @PutMapping("/addCategory")
    public AjaxResult addCategory(@RequestParam String username, @RequestParam String categoryName, MultipartFile file) {
        return new AjaxResult().success(service.addCategory(username, categoryName,file));
    }
    @CheckToken
    @PostMapping("detail/{id}")
    public AjaxResult getCategoryDetail(@PathVariable Integer id) {
        return new AjaxResult().success(service.getCategoryDetail(id));
    }
    @CheckToken
    @PutMapping("/updateCategory")
    public AjaxResult updateCategory(@RequestParam Integer id, @RequestParam String categoryName, MultipartFile file) {
        return new AjaxResult().success(service.updateCategory(id, categoryName, file));
    }
    @CheckToken
    @DeleteMapping("/deleteCategory/{id}")
    public AjaxResult deleteCategory(@PathVariable Integer id) {
        return new AjaxResult().success(service.deleteCategory(id) == 1);
    }
}
