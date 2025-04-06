package com.example.demo.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetPositionInfoDTO {

    private Integer id;//主键ID


    private String name;//姓名

    private String phone; //手机号
    private String email;//邮箱
    private String position;//职位
    private String address;
    private String resume;//简历
    private Integer isAccept;

}
