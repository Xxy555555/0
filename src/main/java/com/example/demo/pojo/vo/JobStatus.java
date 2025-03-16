package com.example.demo.pojo.vo;

import lombok.Data;

@Data
public class JobStatus {
    private Integer positionId;
    private Integer publishStatus;
    private Integer reviewStatus;
    private String reason;
}
