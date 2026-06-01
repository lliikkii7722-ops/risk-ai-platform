package com.riskai.riskaiplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotBlank(message = "Complexity is required")
    private String complexity;

    @Positive(message = "Planned budget must be positive")
    private double plannedBudget;

    @PositiveOrZero(message = "Actual cost cannot be negative")
    private double actualCost;

    @Positive(message = "Planned days must be positive")
    private int plannedDays;

    @PositiveOrZero(message = "Completed days cannot be negative")
    private int completedDays;

    @Positive(message = "Total tasks must be positive")
    private int totalTasks;

    @PositiveOrZero(message = "Completed tasks cannot be negative")
    private int completedTasks;

    @PositiveOrZero(message = "Defects cannot be negative")
    private int defects;

    @Min(value = 0, message = "Testing coverage cannot be below 0")
    @Max(value = 100, message = "Testing coverage cannot exceed 100")
    private int testingCoverage;

    @Positive(message = "Available hours must be positive")
    private int availableHours;

    @PositiveOrZero(message = "Worked hours cannot be negative")
    private int workedHours;
}