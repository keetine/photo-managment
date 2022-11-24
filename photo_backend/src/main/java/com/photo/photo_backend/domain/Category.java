package com.photo.photo_backend.domain;

import lombok.Data;

@Data
public class Category {
    private int id;
    private String categoryName;
    private String thumbnail;
    private String username;
    private int photoNum;
}
