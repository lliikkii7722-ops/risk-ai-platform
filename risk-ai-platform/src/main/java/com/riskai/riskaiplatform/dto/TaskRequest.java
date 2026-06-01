package com.riskai.riskaiplatform.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {

    private String title;

    private String description;

    private LocalDate deadline;

    private Long projectId;

    private Long teamMemberId;

}