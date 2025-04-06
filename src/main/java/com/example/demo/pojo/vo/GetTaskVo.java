package com.example.demo.pojo.vo;

import com.example.demo.pojo.CompanyAndStudent;
import com.example.demo.pojo.MyPage;
import lombok.Data;

@Data
public class GetTaskVo extends MyPage {
    private String title;
    private Integer taskId;
}
