package com.riskai.riskaiplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "project_milestones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMilestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String milestoneName;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private int progressPercentage;

    @Enumerated(EnumType.STRING)
    private MilestoneStatus status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}