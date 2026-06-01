package com.riskai.riskaiplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "quality_risks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityRisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String riskTitle;

    private String moduleName;

    @Enumerated(EnumType.STRING)
    private RiskSeverity severity;

    private String impact;

    private String action;

    private int codeCoverage;

    private int openBugs;

    private int techDebtHours;

    private double defectEscapeRate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}