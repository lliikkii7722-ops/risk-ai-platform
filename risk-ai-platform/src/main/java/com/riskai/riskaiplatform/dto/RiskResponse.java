package com.riskai.riskaiplatform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RiskResponse {

    private String budgetRisk;

    private String scheduleRisk;

    private String resourceRisk;

    private String qualityRisk;

    private int healthScore;

    private String overallStatus;

    private String aiSuggestion;
}