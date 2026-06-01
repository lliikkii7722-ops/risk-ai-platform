package com.riskai.riskaiplatform.controller;

import com.riskai.riskaiplatform.entity.Project;
import com.riskai.riskaiplatform.repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ProjectRepository projectRepository;

    public DashboardController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/summary")
    public Map<String, Object> getDashboardSummary() {

        List<Project> projects = projectRepository.findAll();

        int totalProjects = projects.size();

        double averageBudgetUsed = projects.stream()
                .mapToDouble(p -> (p.getActualCost() / p.getPlannedBudget()) * 100)
                .average()
                .orElse(0);

        double averageResourceUtilization = projects.stream()
                .mapToDouble(p -> (p.getWorkedHours() * 100.0) / p.getAvailableHours())
                .average()
                .orElse(0);

        long highDefectProjects = projects.stream()
                .filter(p -> p.getDefects() > 20)
                .count();

        Map<String, Object> response = new HashMap<>();
        response.put("totalProjects", totalProjects);
        response.put("averageBudgetUsedPercentage", averageBudgetUsed);
        response.put("averageResourceUtilizationPercentage", averageResourceUtilization);
        response.put("highDefectProjects", highDefectProjects);

        return response;
    }
}