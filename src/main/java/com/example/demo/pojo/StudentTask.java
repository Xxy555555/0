package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("student_task")
public class StudentTask {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer taskId;
    private Integer studentId;
    private String content;
    private String name;
    private String evaluate;//评价
    private Integer evaluateStatus;//评价状态 0未评价
    private Integer completeStatus;//完成状态
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private int isDeleted;
}
