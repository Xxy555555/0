package com.example.demo.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskVo {
    @NotNull
    private int taskId;
    @NotNull
    private String content;
}
