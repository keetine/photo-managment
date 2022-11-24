package com.photo.photo_backend.domain.VO;

import lombok.Data;

@Data
public class UserVO {
    private int id;
    private String username;
    private String nickName;
    private String avatar;
}
