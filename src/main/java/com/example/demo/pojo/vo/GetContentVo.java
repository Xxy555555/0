package com.example.demo.pojo.vo;

import com.example.demo.pojo.MyPage;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetContentVo extends MyPage implements Serializable {
    private Integer taskId;
    private Integer studentId;
    private String title;
    private String name;


}
