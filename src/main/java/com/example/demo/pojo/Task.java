package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("task")
public class Task {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer teacherId;
    private String title;
    private String task;

    private LocalDateTime startTime;//开始时间
    private LocalDateTime deadline;//截止时间

    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private int isDeleted;
}
