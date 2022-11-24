package com.photo.photo_backend.service.Impl;

import com.photo.photo_backend.domain.VO.UserLoginVO;
import com.photo.photo_backend.domain.VO.UserVO;
import com.photo.photo_backend.mapper.UserMapper;
import com.photo.photo_backend.service.IUserService;
import com.photo.photo_backend.utils.FileUploadUtil;
import com.photo.photo_backend.utils.MException;
import com.photo.photo_backend.utils.PasswordUtil;
import com.photo.photo_backend.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    private UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Override
    public boolean register(UserLoginVO user) {
        String password = user.getPassword();
        String username = user.getUsername();
        // 加密password
        UserLoginVO registerUser = new UserLoginVO();
        registerUser.setUsername(username);
        registerUser.setPassword(PasswordUtil.encrypt(password));

        //通过用户名查询用户是否存在
        UserLoginVO userLoginVO = userMapper.selectUserByUsername(username);
        if (userLoginVO != null) {
            throw new MException("用户已存在", 400);
        }

        return userMapper.insertUser(registerUser) != 0;
    }

    @Override
    public Map<String,String> login(UserLoginVO user) {
        UserLoginVO loginUser = userMapper.selectUserByUsername(user.getUsername());
        if (loginUser == null) {
            throw new MException("用户不存在",400);
        }
        if (!PasswordUtil.verify(user.getPassword(), loginUser.getPassword())) {
            throw new MException("密码错误",400);
        }
        Map<String,String> map = new HashMap<>();
        map.put("token",TokenUtil.createToken(user.getUsername()));
        return map;
    }

    @Override
    public UserVO getUserByUsername(String username) {
        return userMapper.selectUserVoByUsername(username);
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        UserLoginVO user = userMapper.selectUserByUsername(username);
        if (user == null) {
            throw new MException("用户不存在",400);
        }
        if (!PasswordUtil.verify(oldPassword, user.getPassword())) {
            throw new MException("旧密码错误",400);
        }
        user.setPassword(PasswordUtil.encrypt(newPassword));
        return userMapper.updateUser(user) != 0;
    }

    @Override
    public boolean updateNickname(String username, String nickname) {
        UserVO user = checkUser(username);
        user.setNickName(nickname);
        return userMapper.updateUserByVo(user) != 0;
    }

    @Override
    public boolean updateAvatar(String username, MultipartFile file) {
        UserVO user = checkUser(username);
        FileUploadUtil fileUploadUtil = new FileUploadUtil();
        String path = fileUploadUtil.saveAvatar(file);
        user.setAvatar(path);
        return userMapper.updateAvatar(user) != 0;
    }

    private UserVO checkUser(String username) {
        UserVO user = userMapper.selectUserVoByUsername(username);
        if (user == null) {
            throw new MException("用户不存在",400);
        }
        return user;
    }
}