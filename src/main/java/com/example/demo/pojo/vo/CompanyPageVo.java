package com.example.demo.pojo.vo;

import com.example.demo.pojo.MyPage;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyPageVo extends MyPage {

    private String address;

    private String companyName;

    private String position;//岗位

    private Integer num;//人数

    private Double salary;//薪资

}
