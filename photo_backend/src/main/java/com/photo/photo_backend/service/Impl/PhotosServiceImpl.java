package com.photo.photo_backend.service.Impl;

import com.photo.photo_backend.domain.Category;
import com.photo.photo_backend.domain.Photos;
import com.photo.photo_backend.domain.VO.PhotoVO;
import com.photo.photo_backend.mapper.CategoryMapper;
import com.photo.photo_backend.mapper.PhotosMapper;
import com.photo.photo_backend.service.IPhotosService;
import com.photo.photo_backend.utils.FileUploadUtil;
import com.photo.photo_backend.utils.MException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class PhotosServiceImpl implements IPhotosService {
    private CategoryMapper mapper;
    private PhotosMapper photosMapper;

    @Autowired
    public void setMapper(CategoryMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setPhotosMapper(PhotosMapper photosMapper) {
        this.photosMapper = photosMapper;
    }

    @Override
    public String getCategoryNameById(Integer id) {
        return photosMapper.getCategoryNameById(id);
    }

    @Override
    public int upload(String username, String address, Integer categoryId, MultipartFile[] files) {
        // 分类是否存在
        Category category = mapper.selectCategoryById(categoryId);
        if (category == null) {
            throw new MException("分类不存在，请先创建分类", 400);
        }
        // 用户名是否匹配
        if (!category.getUsername().equals(username)) {
            throw new MException("用户名不匹配", 400);
        }
        // 处理图片

        int count = 0;

        for (MultipartFile file : files) {
            Map<String, String> map = new FileUploadUtil().savePhoto(file);
            Photos photos = new Photos();
            photos.setOriginal(map.get("url"));
            photos.setThumbnail(map.get("thumbnail"));
            photos.setAddress(address);
            photos.setCategoryId(categoryId);
            photos.setUsername(username);
            int i = photosMapper.insert(photos);
            if (i != 0) {
                count++;
            }
        }

        return count;
    }

    @Override
    public int getPhotosCount(String username) {
        return photosMapper.selectCountAll(username);
    }


    @Override
    public List<Photos> getPhotosByRange(Integer start, Integer end, Integer categoryId) {
        return photosMapper.selectPhotosByRange(start, end, categoryId);
    }

    @Override
    public List<Photos> getPhotosByRange(Integer start, Integer end,String username) {
        return photosMapper.selectAllPhotosByRange(start, end,username);
    }

    @Override
    public List<Object> getAllPhotos(String username) {
        List<PhotoVO> photos = photosMapper.selectAllPhotos(username);

        List<Object> list = new ArrayList<>();
        Map<String, List<PhotoVO>> map = new HashMap<>();
        for (PhotoVO photo : photos) {
            String date = photo.getCreateTime().substring(0, 10);
            if (map.containsKey(date)) {
                map.get(date).add(photo);
            } else {
                List<PhotoVO> list1 = new ArrayList<>();
                list1.add(photo);
                map.put(date, list1);
            }
        }
        for (Map.Entry<String, List<PhotoVO>> entry : map.entrySet()) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("date", entry.getKey());
            map1.put("photos", entry.getValue());
            list.add(map1);
        }
        // 按照时间排序
        list.sort((o1, o2) -> {
            Map<String, Object> map1 = (Map<String, Object>) o1;
            Map<String, Object> map2 = (Map<String, Object>) o2;
            String date1 = (String) map1.get("date");
            String date2 = (String) map2.get("date");
            return date2.compareTo(date1);
        });
        return list;
    }

    @Override
    public int delete(List<Integer> ids) {
        // 通过id查询图片
        List<Photos> photos = photosMapper.selectPhotosByIds(ids);
        // 删除图片文件
        for (Photos photo : photos) {
            new FileUploadUtil().deletePhoto(photo.getOriginal());
            new FileUploadUtil().deletePhoto(photo.getThumbnail());
        }

        return photosMapper.delete(ids);
    }
}
