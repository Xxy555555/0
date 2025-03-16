package com.example.demo.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



import java.time.LocalDateTime;
@Data
public class PublishTask  {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime startTime;//开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime deadline;//截止时间
    @NotNull
    private String task;
    @NotNull
    private String title;
}
