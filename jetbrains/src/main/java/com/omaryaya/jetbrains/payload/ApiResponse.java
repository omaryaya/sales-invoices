package com.omaryaya.jetbrains.payload;


public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private T object;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(Boolean success, T obj) {
        this.success = success;
        this.object = obj;
    }

    public ApiResponse(Boolean success, String message, T obj) {
        this.success = success;
        this.message = message;
        this.object = obj;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}