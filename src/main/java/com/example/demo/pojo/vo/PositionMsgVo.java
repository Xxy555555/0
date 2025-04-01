package com.example.demo.pojo.vo;

import com.example.demo.pojo.MyPage;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionMsgVo extends MyPage {

    private String position;

    private Integer reviewStatus;
}
