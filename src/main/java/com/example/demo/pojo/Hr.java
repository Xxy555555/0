package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("hr")
public class Hr {
    @TableId(type= IdType.AUTO)
    private Integer Id;
    private Integer HrId;
    private String name;

    private String recruit;//招聘信息

    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private int isDeleted;
}
