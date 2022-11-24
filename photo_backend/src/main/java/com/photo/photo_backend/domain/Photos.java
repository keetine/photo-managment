package com.photo.photo_backend.domain;

import lombok.Data;

@Data
public class Photos {
    private Integer id;
    private Integer categoryId;
    // 缩略图地址
    private String thumbnail;
    // 原图地址
    private String original;
    // 创建时间
    private String createTime;
    //地址
    private String address;

    private String username;

}
