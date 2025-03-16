package com.example.demo.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class OpinionVo {
    @NotNull
    private int studentId;
    @NotNull
    private String opinion;
}
