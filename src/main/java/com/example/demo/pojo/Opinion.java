package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
@TableName("opinion")
@Data
public class Opinion {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer HrId;
    private Integer studentId;
    private String opinion;

    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private int isDeleted;
}
