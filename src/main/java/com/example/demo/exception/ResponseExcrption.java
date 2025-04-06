package com.example.demo.exception;

import com.example.demo.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ResponseExcrption {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e) {
        log.error("全局异常处理：{}", e);
       return ResponseResult.fail(e.getMessage());
    }
@ExceptionHandler(Myexception.class)
    public ResponseResult myexception(Myexception e) {
        log.error("自定义异常处理：{}", e);
        return  ResponseResult.fail(e.getMessage());
    }
}
