package com.example.demo.pojo.dto;

import lombok.Data;

@Data
public class InternshipInfoDTO {
    private Integer id;

    private String name;

    private String companyName;
    private String position;//职位
    private Integer InternshipStatus;//实习状态
}
