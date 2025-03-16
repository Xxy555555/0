package com.example.demo.result;

import com.example.demo.enums.CodeEnum;
import lombok.Data;

@Data
public class ResponseResult<T> {
    private int code;
    private String msg;
    private T data;
    private ResponseResult() {

    }
    public static <T> ResponseResult<T> build(T data, CodeEnum codeEnum) {
        ResponseResult<T> tResponseResult = new ResponseResult<>();
        if(data!=null) {
            tResponseResult.setData(data);
        }
        tResponseResult.setCode(codeEnum.getCode());
        tResponseResult.setMsg(codeEnum.getMessage());
        return tResponseResult;
    }
    public static <T> ResponseResult<T> ok(T data) {

        return build(data,CodeEnum.SUCCESS);
    }
    public static <T> ResponseResult<T> fail() {
        return build(null,CodeEnum.ERROR);
    }
    public static <T> ResponseResult<T> fail(T data) {
        return build(data,CodeEnum.ERROR);
    }
}
