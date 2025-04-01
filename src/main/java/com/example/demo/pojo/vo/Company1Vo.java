package com.example.demo.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class Company1Vo {
    @NotNull
    private String companyAddress;
    @NotNull
    private String companyName;
    @NotNull
    private String companyPhone;
    @NotNull
    private String companyEmail;
    private Integer id;
}
