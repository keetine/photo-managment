package com.photo.photo_backend.service;

import com.photo.photo_backend.domain.VO.UserLoginVO;
import com.photo.photo_backend.domain.VO.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IUserService {
    boolean register(UserLoginVO user);

    Map<String,String> login(UserLoginVO user);

    UserVO getUserByUsername(String username);

    boolean changePassword(String username, String oldPassword, String newPassword);

    boolean updateNickname(String username, String nickname);

    boolean updateAvatar(String username, MultipartFile file);
}
