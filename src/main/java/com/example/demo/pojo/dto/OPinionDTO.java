package com.example.demo.pojo.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OPinionDTO {
    private String name;
    private Integer id;
    private Integer HrId;
    private Integer studentId;
    private String opinion;
    private int isDeleted;
}

