package com.example.demo.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfo {
        @ExcelProperty("ID")
        private Integer Id;//主键ID

        @ExcelProperty("姓名")
        @TableField("name")
        private String name;//姓名


        @ExcelProperty("手机号")
        private String phone; //手机号
        @ExcelProperty("身份证号")
        private String idCard;//身份证号
        @ExcelProperty("性别")
        private Integer gender; // 数据库中存储的性别编码，类型为 int
        @ExcelProperty("年龄")
        private Integer age;//年龄
        @ExcelProperty("身高")
        private Double height;//身高
        @ExcelProperty("体重")
        private Double weight;//体重

        private Integer type;//1学生
        @ExcelProperty("实习状态")
        private Integer InternshipStatus;//实习状态


        //private Integer disable; //0合法状态 1禁用状态
        @ExcelProperty("创建时间")
        @TableField(fill = FieldFill.INSERT)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;//创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @ExcelProperty("更新时间")
        @TableField(fill = FieldFill.INSERT_UPDATE)
        private LocalDateTime updateTime;//更新时间
        @TableLogic
        private Integer isDeleted; //逻辑删除

}
