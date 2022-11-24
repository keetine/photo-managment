package com.photo.photo_backend.config;

import com.photo.photo_backend.annotations.CheckToken;
import com.photo.photo_backend.utils.MException;
import com.photo.photo_backend.utils.TokenUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        // 方法是否有验证token的注解
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            CheckToken checkToken = handlerMethod.getMethodAnnotation(CheckToken.class);
            if (checkToken == null) {
                return true;
            }
            if (checkToken.required()) {
                if (token == null) {
                    throw new MException("token不存在", 401);
                }
                if (!TokenUtil.verify(token)) {
                    throw new MException("token无效", 401);
                }
            }
        }

        return true;
    }
}
