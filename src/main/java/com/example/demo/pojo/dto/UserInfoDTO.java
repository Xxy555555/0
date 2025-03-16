package com.example.demo.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UserInfoDTO {
    //@ExcelProperty("ID")
    private Integer Id;//主键ID

    // @ExcelProperty("姓名")

    private String name;//姓名


    // @ExcelProperty("手机号")
    private String phone; //手机号
    // @ExcelProperty("身份证号")
    private String idCard;//身份证号
    //@ExcelProperty("性别")
    private Integer gender; // 数据库中存储的性别编码，类型为 int
    //@ExcelProperty("年龄")
    private Integer age;//年龄
    // @ExcelProperty("身高")
    private Double height;//身高
    // @ExcelProperty("体重")
    private Double weight;//体重

    private Integer type;//1学生
    private Integer InternshipStatus;//实习状态


    private Integer disable; //0合法状态 1禁用状态
}
