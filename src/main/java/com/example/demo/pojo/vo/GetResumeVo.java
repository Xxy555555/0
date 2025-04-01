package com.example.demo.pojo.vo;

import com.example.demo.pojo.MyPage;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetResumeVo extends MyPage{
    private Integer studentId;

}
