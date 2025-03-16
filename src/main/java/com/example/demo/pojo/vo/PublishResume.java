package com.example.demo.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PublishResume {
    @NotNull
    private Integer positionId;

}
