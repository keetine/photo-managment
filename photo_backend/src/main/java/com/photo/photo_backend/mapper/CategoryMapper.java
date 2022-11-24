package com.photo.photo_backend.mapper;

import com.photo.photo_backend.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectCategoriesByUsername(String username);
    Category selectCategoryByUsernameAndCategoryName(String username, String categoryName);

    int insertCategory(Category category);

    Category selectCategoryById(Integer id);

    int updateCategory(Category category);

    int deleteCategory(Integer id);
}
