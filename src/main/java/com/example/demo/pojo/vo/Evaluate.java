package com.example.demo.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Evaluate {
    @NotNull
    private Integer taskId;
    @NotNull
    private Integer studentId;
    @NotNull
    private String  evaluate;
}
