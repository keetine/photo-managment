package com.photo.photo_backend.utils;

import lombok.Data;

@Data
public class AjaxResult {
    private int code;
    private boolean success;
    private String message;
    private Object dataSet;

    public AjaxResult() {
    }

    public AjaxResult(int code, boolean success, String message, Object dataSet) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.dataSet = dataSet;
    }
    public AjaxResult success(Object dataSet) {
        this.code = 200;
        this.success = true;
        this.message = "success";
        this.dataSet = dataSet;
        return this;
    }
    public AjaxResult fail(int code,String message) {
        this.code = code;
        this.success = false;
        this.message = message;
        this.dataSet = null;
        return this;
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", dataSet=" + dataSet +
                '}';
    }
}