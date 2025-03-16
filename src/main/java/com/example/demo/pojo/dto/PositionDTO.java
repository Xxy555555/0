package com.example.demo.pojo.dto;

import lombok.Data;

@Data
public class PositionDTO {
    private Integer positionId;
    private String companyName;
    private String position;//职位
    private String address;
    private Double salary;
    private Integer num;
}
