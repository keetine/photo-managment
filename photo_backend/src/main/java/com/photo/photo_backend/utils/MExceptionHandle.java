package com.photo.photo_backend.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class MExceptionHandle {
    @ExceptionHandler(MException.class)
    public AjaxResult exceptionHandler(MException e, HttpServletResponse response) {
        response.setStatus(e.getCode());
        return new AjaxResult().fail(e.getCode(),e.getMessage());
    }
}
