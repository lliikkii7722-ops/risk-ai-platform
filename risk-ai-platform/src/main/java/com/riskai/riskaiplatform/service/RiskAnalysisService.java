package com.riskai.riskaiplatform.service;

import com.riskai.riskaiplatform.ai.OllamaService;
import com.riskai.riskaiplatform.dto.RiskResponse;
import com.riskai.riskaiplatform.entity.Project;
import org.springframework.stereotype.Service;

@Service
public class RiskAnalysisService {

    private final OllamaService ollamaService;

    public RiskAnalysisService(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    public RiskResponse analyze(Project project) {

        double budgetUsed =
                (project.getActualCost() / project.getPlannedBudget()) * 100;

        double taskCompletion =
                (project.getCompletedTasks() * 100.0) / project.getTotalTasks();

        double timeUsed =
                (project.getCompletedDays() * 100.0) / project.getPlannedDays();

        double utilization =
                (project.getWorkedHours() * 100.0) / project.getAvailableHours();

        String budgetRisk =
                budgetUsed > 90 ? "HIGH" :
                        budgetUsed > 70 ? "MEDIUM" : "LOW";

        String scheduleRisk =
                timeUsed > taskCompletion + 20 ? "HIGH" :
                        timeUsed > taskCompletion + 10 ? "MEDIUM" : "LOW";

        String resourceRisk =
                utilization > 85 ? "HIGH" :
                        utilization > 70 ? "MEDIUM" : "LOW";

        String qualityRisk =
                project.getDefects() > 20 ? "HIGH" :
                        project.getDefects() > 10 ? "MEDIUM" : "LOW";

        int healthScore = 100;

        if (budgetRisk.equals("HIGH")) healthScore -= 25;
        if (scheduleRisk.equals("HIGH")) healthScore -= 25;
        if (resourceRisk.equals("HIGH")) healthScore -= 20;
        if (qualityRisk.equals("HIGH")) healthScore -= 20;

        String overallStatus =
                healthScore >= 75 ? "HEALTHY" :
                        healthScore >= 50 ? "WARNING" : "CRITICAL";

        String prompt = """
                Analyze this software project and provide mitigation strategies.

                Budget Risk: %s
                Schedule Risk: %s
                Resource Risk: %s
                Quality Risk: %s
                Overall Status: %s

                Give 5 short mitigation suggestions.
                """.formatted(
                budgetRisk,
                scheduleRisk,
                resourceRisk,
                qualityRisk,
                overallStatus
        );

        String aiSuggestion =
                ollamaService.generateSuggestion(prompt);

        return RiskResponse.builder()
                .budgetRisk(budgetRisk)
                .scheduleRisk(scheduleRisk)
                .resourceRisk(resourceRisk)
                .qualityRisk(qualityRisk)
                .healthScore(healthScore)
                .overallStatus(overallStatus)
                .aiSuggestion(aiSuggestion)
                .build();
    }
}