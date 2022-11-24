package com.photo.photo_backend.controller;

import com.photo.photo_backend.annotations.CheckToken;
import com.photo.photo_backend.domain.VO.UserLoginVO;
import com.photo.photo_backend.service.IUserService;
import com.photo.photo_backend.utils.AjaxResult;
import com.photo.photo_backend.utils.FileUploadUtil;
import com.photo.photo_backend.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private IUserService userService;
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public AjaxResult register(@RequestBody UserLoginVO user) {
        return new AjaxResult().success(userService.register(user));
    }

    @PostMapping("/login")
    public AjaxResult login(@RequestBody UserLoginVO user) {
        return new AjaxResult().success(userService.login(user));
    }

    @CheckToken
    @GetMapping("/info/{username}")
    public AjaxResult info(@PathVariable String username)    {
        return new AjaxResult().success(userService.getUserByUsername(username));
    }
    @CheckToken
    @PutMapping("/changePassword")
    public AjaxResult changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, HttpServletRequest request) {
        String username = TokenUtil.getUsername(request.getHeader("Authorization"));
        return new AjaxResult().success(userService.changePassword(username, oldPassword, newPassword));
    }
    @CheckToken
    @PutMapping("/updateNickname")
    public AjaxResult updateNickname(@RequestParam String nickname, HttpServletRequest request) {
        String username = TokenUtil.getUsername(request.getHeader("Authorization"));
        return new AjaxResult().success(userService.updateNickname(username, nickname));
    }
    @CheckToken
    @PutMapping("/updateAvatar")
    // 上传头像
    public AjaxResult updateAvatar(MultipartFile file, HttpServletRequest request) {
        String username = TokenUtil.getUsername(request.getHeader("Authorization"));
        return new AjaxResult().success(userService.updateAvatar(username, file));
    }
}
