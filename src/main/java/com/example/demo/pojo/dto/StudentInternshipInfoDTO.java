package com.example.demo.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentInternshipInfoDTO {

    private String opinion;
    private Integer Id;//主键ID
    private String name;//姓名
    private String phone; //手机号
    private String idCard;//身份证号
    private Integer gender; // 数据库中存储的性别编码，类型为 int
    private Integer age;//年龄
    private Double height;//身高
    private Double weight;//体重
    private Integer type;//1学生
    private Integer InternshipStatus;//实习状态
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    private Integer isDeleted; //逻辑删除
}
