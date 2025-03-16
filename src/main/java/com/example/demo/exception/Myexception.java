package com.example.demo.exception;

import com.example.demo.enums.MyExcptionEnum;

public class Myexception extends RuntimeException {
    private String message;
    private int code;

   //public Myexception() {}
    public Myexception(String message,int code) {
        super(message);
        this.message = message;
        this.code = code;

    }
    public Myexception(MyExcptionEnum e) {
        super(e.getMessage());
        this.message = e.getMessage();
        this.code = e.getCode();
    }
}
