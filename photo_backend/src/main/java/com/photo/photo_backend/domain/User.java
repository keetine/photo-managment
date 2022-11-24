package com.photo.photo_backend.domain;

import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
}
