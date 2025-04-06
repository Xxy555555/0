package com.example.demo.pojo.vo;

import com.example.demo.pojo.MyPage;
import lombok.Data;

@Data
public class Position1Vo extends MyPage {
    private Integer positionId;
    private String positionName;
    private Integer isAccept;

}

