package com.example.demo.exception;

import com.example.demo.result.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseExcrption {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e) {



       return ResponseResult.fail(e.getMessage());
    }
@ExceptionHandler(Myexception.class)
    public ResponseResult myexception(Myexception e) {



        return  ResponseResult.fail(e.getMessage());
    }
}
