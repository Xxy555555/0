package com.example.demo.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetContentVo implements Serializable {
    private Integer taskId;
    private Integer studentId;
    @NotNull
    private Integer current;
    @NotNull
    private Integer size;

}
