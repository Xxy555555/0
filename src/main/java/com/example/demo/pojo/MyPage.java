package com.example.demo.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MyPage {
    @NotNull
    private Integer current=1;
    @NotNull
    private Integer size=10;
}
