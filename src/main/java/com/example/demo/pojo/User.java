package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
@ToString
public class User {
    @TableId(type= IdType.AUTO)
    private Integer Id;
    @NotNull
    private String userName;
    //springMvc把当前对象转换成json对象的时候，忽略password最终的json字符串就没有password这个属性了
    //@JsonIgnore
    @NotNull
    private String password;//密码
    @NotNull
    @Email
    private String email;//邮箱
    @NotNull
    private Integer type;   //1 学生 2老师 3HR 0管理员
    private Integer teacherId;
    private Integer hrId;
    private Integer disable;
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private int isDeleted;



}

