package com.riskai.riskaiplatform.dto;

import com.riskai.riskaiplatform.entity.MilestoneStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MilestoneRequest {

    private String milestoneName;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private int progressPercentage;

    private MilestoneStatus status;

    private Long projectId;
}