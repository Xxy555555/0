package com.example.demo.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum MapperEnum {
    STU(1,"stuNum"),
    TEACHER(2,"teacherNum"),
    HR(3,"hrNum");
    private Integer code;
    private String position;
    private MapperEnum(Integer code, String position) {
        this.code = code;
        this.position = position;
    }
    public Integer getCode() {
        return code;
    }
    public String getPosition() {
        return position;
    }


    private static final Map<Integer, MapperEnum> CODE_MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(MapperEnum::getCode, e -> e));

    public static MapperEnum fromCode(int code) {
        return CODE_MAP.get(code);
    }
}
