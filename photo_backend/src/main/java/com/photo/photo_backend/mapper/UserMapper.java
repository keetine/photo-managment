package com.photo.photo_backend.mapper;

import com.photo.photo_backend.domain.VO.UserLoginVO;
import com.photo.photo_backend.domain.VO.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insertUser(UserLoginVO user);
    UserLoginVO selectUserByUsername(String username);
    UserVO selectUserVoByUsername(String username);

    int updateUser(UserLoginVO user);

    int updateUserByVo(UserVO user);

    int updateAvatar(UserVO user);
}
