package com.example.demo.pojo.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class StudentTaskDTO {
   private String title;
   private String task;
    private Integer id;
    private Integer taskId;
    private Integer studentId;
    private String content;
    private String name;
    private String evaluate;//评价
    private Integer evaluateStatus;//评价状态 0未评价
    private Integer completeStatus;//完成状态

    private LocalDateTime createTime;


    private LocalDateTime updateTime;


    private int isDeleted;
}
