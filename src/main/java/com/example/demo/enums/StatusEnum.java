package com.example.demo.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum StatusEnum {
    STU(1,"submit"),
    TEACHER(2,"pass"),
    HR(3,"noPass");
    private Integer code;
    private String status;
    private StatusEnum(Integer code, String position) {
        this.code = code;
        this.status = position;
    }
    public Integer getCode() {
        return code;
    }
    public String getStatus() {
        return status;
    }




    public static StatusEnum fromCode(int code) {
       for (StatusEnum statusEnum : StatusEnum.values()) {
           if (statusEnum.getCode() == code) {
               return statusEnum;
           }
       }
       return null;
    }

}
