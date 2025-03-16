package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
@TableName("company_and_student")
@Data
public class CompanyAndStudent {
        @TableId(type = IdType.AUTO)
        private Integer id;
        private Integer studentId;//学生Id
        private Integer positionId;
        private Integer isPass;//是否通过
        private Integer isAccept;//是否接受
        @TableField(fill= FieldFill.INSERT)
        private LocalDateTime createTime;

        @TableField(fill= FieldFill.INSERT_UPDATE)
        private LocalDateTime updateTime;

        @TableLogic
        private int isDeleted;



}
