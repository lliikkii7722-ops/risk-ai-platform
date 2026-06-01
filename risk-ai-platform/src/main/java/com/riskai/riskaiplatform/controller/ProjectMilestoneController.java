package com.riskai.riskaiplatform.controller;

import com.riskai.riskaiplatform.dto.MilestoneRequest;
import com.riskai.riskaiplatform.entity.ProjectMilestone;
import com.riskai.riskaiplatform.service.ProjectMilestoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class ProjectMilestoneController {

    private final ProjectMilestoneService milestoneService;

    public ProjectMilestoneController(ProjectMilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @PostMapping
    public ProjectMilestone createMilestone(@RequestBody MilestoneRequest request) {
        return milestoneService.createMilestone(request);
    }

    @GetMapping
    public List<ProjectMilestone> getAllMilestones() {
        return milestoneService.getAllMilestones();
    }

    @GetMapping("/project/{projectId}")
    public List<ProjectMilestone> getMilestonesByProject(@PathVariable Long projectId) {
        return milestoneService.getMilestonesByProject(projectId);
    }

    @DeleteMapping("/{id}")
    public String deleteMilestone(@PathVariable Long id) {
        return milestoneService.deleteMilestone(id);
    }
}