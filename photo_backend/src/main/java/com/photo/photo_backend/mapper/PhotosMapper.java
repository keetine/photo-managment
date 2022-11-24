package com.photo.photo_backend.mapper;

import com.photo.photo_backend.domain.Photos;
import com.photo.photo_backend.domain.VO.PhotoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PhotosMapper {
    int insert(Photos photos);

    int selectCountAll(String username);

    String getCategoryNameById(Integer id);

    List<Photos> selectAllPhotosByRange(Integer start, Integer end,String userName);

    List<Photos> selectPhotosByRange(Integer start, Integer end, Integer categoryId);

    List<PhotoVO> selectAllPhotos(String username);

    List<Photos> selectPhotosByIds(List<Integer> ids);

    int delete(List<Integer> ids);
}
