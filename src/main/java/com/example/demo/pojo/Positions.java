package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("position")
public class Positions {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer companyId;
    private String position;//职位
    private String address;
    private Double salary;
    private Integer num;

    private Integer publishStatus;//发布状态 0    1招聘  2未招聘

    private Integer reviewStatus;//审核状态 0   1提交 通过  驳回
    private String reason; //驳回理由

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private int isDeleted;
}
