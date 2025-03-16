package com.example.demo.enums;

public enum CodeEnum {
    SUCCESS(200, "请求成功"),
    ERROR(2000, "操作失败"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "未找到");


    private final int code;
    private final String message;
    private CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
