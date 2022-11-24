package com.photo.photo_backend.domain.VO;

import lombok.Data;

@Data
public class PhotoVO {
    private Integer id;
    private Integer categoryId;
    private String address;
    private String categoryName;
    private String createTime;
    private String thumbnail;
    private String original;
}
