package com.riskai.riskaiplatform.controller;

import com.riskai.riskaiplatform.dto.RiskResponse;
import com.riskai.riskaiplatform.entity.Project;
import com.riskai.riskaiplatform.repository.ProjectRepository;
import com.riskai.riskaiplatform.service.RiskAnalysisService;
import org.springframework.web.bind.annotation.*;
import com.riskai.riskaiplatform.exception.ProjectNotFoundException;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final RiskAnalysisService riskAnalysisService;

    public ProjectController(ProjectRepository projectRepository,
                             RiskAnalysisService riskAnalysisService) {
        this.projectRepository = projectRepository;
        this.riskAnalysisService = riskAnalysisService;
    }

    @PostMapping
    public Project createProject(@Valid @RequestBody Project project) {
        return projectRepository.save(project);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id,
                                 @Valid @RequestBody Project updatedProject) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));

        project.setProjectName(updatedProject.getProjectName());
        project.setComplexity(updatedProject.getComplexity());
        project.setPlannedBudget(updatedProject.getPlannedBudget());
        project.setActualCost(updatedProject.getActualCost());
        project.setPlannedDays(updatedProject.getPlannedDays());
        project.setCompletedDays(updatedProject.getCompletedDays());
        project.setTotalTasks(updatedProject.getTotalTasks());
        project.setCompletedTasks(updatedProject.getCompletedTasks());
        project.setDefects(updatedProject.getDefects());
        project.setTestingCoverage(updatedProject.getTestingCoverage());
        project.setAvailableHours(updatedProject.getAvailableHours());
        project.setWorkedHours(updatedProject.getWorkedHours());

        return projectRepository.save(project);
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return "Project deleted successfully";
    }

    @GetMapping("/{id}/risk-analysis")
    public RiskResponse analyzeRisk(@PathVariable Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return riskAnalysisService.analyze(project);
    }
}