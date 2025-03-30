package com.example.demo.enums;

public enum MapperEnum {
    STU("1","stuNum"),
    TEACHER("2","teacherNum"),
    HR("3","hrNum");
    private String code;
    private String position;
    private MapperEnum(String code, String position) {
        this.code = code;
        this.position = position;
    }
    public String getCode() {
        return code;
    }
    public String getPosition() {
        return position;
    }
}
