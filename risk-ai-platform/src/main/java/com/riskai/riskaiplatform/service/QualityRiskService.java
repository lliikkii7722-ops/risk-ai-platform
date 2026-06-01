package com.riskai.riskaiplatform.service;

import com.riskai.riskaiplatform.dto.QualityRiskRequest;
import com.riskai.riskaiplatform.entity.Project;
import com.riskai.riskaiplatform.entity.QualityRisk;
import com.riskai.riskaiplatform.repository.ProjectRepository;
import com.riskai.riskaiplatform.repository.QualityRiskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityRiskService {

    private final QualityRiskRepository qualityRiskRepository;
    private final ProjectRepository projectRepository;

    public QualityRiskService(QualityRiskRepository qualityRiskRepository,
                              ProjectRepository projectRepository) {
        this.qualityRiskRepository = qualityRiskRepository;
        this.projectRepository = projectRepository;
    }

    public QualityRisk createQualityRisk(QualityRiskRequest request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        QualityRisk qualityRisk = QualityRisk.builder()
                .riskTitle(request.getRiskTitle())
                .moduleName(request.getModuleName())
                .severity(request.getSeverity())
                .impact(request.getImpact())
                .action(request.getAction())
                .codeCoverage(request.getCodeCoverage())
                .openBugs(request.getOpenBugs())
                .techDebtHours(request.getTechDebtHours())
                .defectEscapeRate(request.getDefectEscapeRate())
                .project(project)
                .build();

        return qualityRiskRepository.save(qualityRisk);
    }

    public List<QualityRisk> getAllQualityRisks() {
        return qualityRiskRepository.findAll();
    }

    public List<QualityRisk> getQualityRisksByProject(Long projectId) {
        return qualityRiskRepository.findByProjectId(projectId);
    }

    public String deleteQualityRisk(Long id) {
        qualityRiskRepository.deleteById(id);
        return "Quality risk deleted successfully";
    }
}