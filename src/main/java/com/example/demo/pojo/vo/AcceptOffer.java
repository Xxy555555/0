package com.example.demo.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AcceptOffer {
    @NotNull
    private Integer positionId;
    @NotNull
    private Integer isAccept;
}
