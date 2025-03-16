package com.example.demo.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetResumeVo {
    private Integer studentId;
    @NotNull
    private Integer size;
    @NotNull
    private Integer current;
}
