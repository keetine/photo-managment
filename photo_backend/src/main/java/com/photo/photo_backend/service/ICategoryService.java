package com.photo.photo_backend.service;

import com.photo.photo_backend.domain.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICategoryService {
    boolean addCategory(String username, String categoryName, MultipartFile file);
    List<Category> getCategories(String username);

    Category getCategoryDetail(Integer id);

    int updateCategory(Integer id, String categoryName, MultipartFile file);

    int deleteCategory(Integer id);
}
