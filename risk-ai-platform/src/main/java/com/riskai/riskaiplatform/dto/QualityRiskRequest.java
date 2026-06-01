package com.riskai.riskaiplatform.dto;

import com.riskai.riskaiplatform.entity.RiskSeverity;
import lombok.Data;

@Data
public class QualityRiskRequest {

    private String riskTitle;

    private String moduleName;

    private RiskSeverity severity;

    private String impact;

    private String action;

    private int codeCoverage;

    private int openBugs;

    private int techDebtHours;

    private double defectEscapeRate;

    private Long projectId;
}