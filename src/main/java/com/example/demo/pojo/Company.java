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
    private String companyAddress;
    private String companyName;
    private String companyPhone;
    private String companyEmail;



    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private int isDeleted;

}
