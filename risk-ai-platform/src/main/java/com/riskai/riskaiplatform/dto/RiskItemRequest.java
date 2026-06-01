package com.riskai.riskaiplatform.dto;

import com.riskai.riskaiplatform.entity.RiskSeverity;
import com.riskai.riskaiplatform.entity.RiskStatus;
import lombok.Data;

@Data
public class RiskItemRequest {
    private String title;
    private String category;
    private RiskSeverity severity;
    private String likelihood;
    private int score;
    private String owner;
    private RiskStatus status;
    private Long projectId;
}