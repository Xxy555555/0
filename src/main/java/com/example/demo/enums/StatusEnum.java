package com.example.demo.enums;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
@Getter
public enum StatusEnum {
    NO_STATUS(0,"no_status"),
    SUBMIT(1,"submit"),
    PASS(2,"pass"),
    NO_PASS(3,"noPass");
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
