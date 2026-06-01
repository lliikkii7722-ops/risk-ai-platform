package com.riskai.riskaiplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "team_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}