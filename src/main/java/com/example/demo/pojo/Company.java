package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
@TableName("company")
@Data
public class Company {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer hrId;
//    private String address;
    private String companyName;
//    private String position;//岗位
//    private Integer num;//人数
//    private Double Salary;//薪资


    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private int isDeleted;

}
