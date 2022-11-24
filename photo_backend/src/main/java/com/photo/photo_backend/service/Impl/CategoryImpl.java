package com.photo.photo_backend.service.Impl;

import com.photo.photo_backend.domain.Category;
import com.photo.photo_backend.mapper.CategoryMapper;
import com.photo.photo_backend.service.ICategoryService;
import com.photo.photo_backend.utils.FileUploadUtil;
import com.photo.photo_backend.utils.MException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CategoryImpl implements ICategoryService {
    private CategoryMapper mapper;
    @Autowired
    public void setMapper(CategoryMapper mapper) {
        this.mapper = mapper;
    }
    @Override
    public boolean addCategory(String username, String categoryName, MultipartFile file) {
        // 分类是否存在
        Category category = mapper.selectCategoryByUsernameAndCategoryName(username, categoryName);
        if (category != null) {
            throw new MException("分类已存在", 400);
        }
        // 处理缩略图

        FileUploadUtil fileUploadUtil = new FileUploadUtil();
        String path = fileUploadUtil.saveAvatar(file);

        Category newCategory = new Category();
        newCategory.setUsername(username);
        newCategory.setCategoryName(categoryName);
        newCategory.setThumbnail(path);
        return mapper.insertCategory(newCategory) != 0;
    }

    @Override
    public List<Category> getCategories(String username) {
        return mapper.selectCategoriesByUsername(username);
    }

    @Override
    public Category getCategoryDetail(Integer id) {
        return mapper.selectCategoryById(id);
    }

    @Override
    public int updateCategory(Integer id, String categoryName, MultipartFile file) {
        // 分类是否存在
        Category category = mapper.selectCategoryById(id);
        if (category == null) {
            throw new MException("分类不存在", 400);
        }
        // 处理缩略图
        FileUploadUtil fileUploadUtil = new FileUploadUtil();
        String path = fileUploadUtil.saveAvatar(file);
        category.setCategoryName(categoryName);
        category.setThumbnail(path);
        return mapper.updateCategory(category);

    }

    @Override
    public int deleteCategory(Integer id) {
        Category category = mapper.selectCategoryById(id);
        if (category == null) {
            throw new MException("分类不存在", 400);
        }
        return mapper.deleteCategory(id);
    }
}
