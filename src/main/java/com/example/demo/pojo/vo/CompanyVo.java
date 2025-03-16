package com.example.demo.pojo.vo;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyVo {

    @NotNull
    private String address;

    @NotNull
    private String position;//岗位
    @NotNull
    private Integer num;//人数
    @NotNull
    private Double salary;//薪资




}
