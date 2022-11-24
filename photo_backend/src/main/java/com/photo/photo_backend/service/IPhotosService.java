package com.photo.photo_backend.service;

import com.photo.photo_backend.domain.Photos;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IPhotosService {

    String getCategoryNameById(Integer id);
    int upload(String username, String address, Integer categoryId, MultipartFile[] files);
    int getPhotosCount(String username);
    List<Photos> getPhotosByRange(Integer start, Integer end, Integer categoryId);
    List<Photos> getPhotosByRange(Integer start, Integer end,String username);

    List<Object> getAllPhotos(String username);

    int delete(List<Integer> ids);
}
