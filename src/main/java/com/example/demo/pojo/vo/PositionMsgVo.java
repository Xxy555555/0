package com.example.demo.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionMsgVo {
    @NotNull
    private Integer size;
    private String position;
    @NotNull
    private Integer current;
    private Integer reviewStatus;
}
