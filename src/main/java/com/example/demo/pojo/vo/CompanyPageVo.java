package com.example.demo.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyPageVo {

    private String address;

    private String companyName;

    private String position;//岗位

    private Integer num;//人数

    private Double salary;//薪资
    @NotNull
    private Integer current;
    @NotNull
    private Integer size;
}
