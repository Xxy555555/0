package com.example.demo.pojo.vo;

import com.example.demo.pojo.MyPage;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class OpinionVo  {
    @NotNull
    private int studentId;
    @NotNull
    private String opinion;
}
