package com.riskai.riskaiplatform.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TeamMemberRequest {

    @NotBlank(message = "Member name is required")
    private String name;

    @NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "Email is required")
    private String email;

    @Positive(message = "Available hours must be positive")
    private int availableHours;

    @PositiveOrZero(message = "Allocated hours cannot be negative")
    private int allocatedHours;

    @NotNull(message = "Project id is required")
    private Long projectId;
}