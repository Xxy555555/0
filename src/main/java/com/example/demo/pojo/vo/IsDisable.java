package com.example.demo.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IsDisable {
    @NotNull
    private int id;
    @NotNull
    private int disable;
}
