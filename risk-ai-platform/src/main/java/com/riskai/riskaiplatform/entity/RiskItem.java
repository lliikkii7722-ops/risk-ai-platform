package com.riskai.riskaiplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "risk_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String category;

    @Enumerated(EnumType.STRING)
    private RiskSeverity severity;

    private String likelihood;

    private int score;

    private String owner;

    @Enumerated(EnumType.STRING)
    private RiskStatus status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}