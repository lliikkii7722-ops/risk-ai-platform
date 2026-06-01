package com.riskai.riskaiplatform.service;

import com.riskai.riskaiplatform.dto.RiskItemRequest;
import com.riskai.riskaiplatform.entity.Project;
import com.riskai.riskaiplatform.entity.RiskItem;
import com.riskai.riskaiplatform.repository.ProjectRepository;
import com.riskai.riskaiplatform.repository.RiskItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskItemService {

    private final RiskItemRepository riskItemRepository;
    private final ProjectRepository projectRepository;

    public RiskItemService(RiskItemRepository riskItemRepository,
                           ProjectRepository projectRepository) {
        this.riskItemRepository = riskItemRepository;
        this.projectRepository = projectRepository;
    }

    public RiskItem createRisk(RiskItemRequest request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        RiskItem risk = RiskItem.builder()
                .title(request.getTitle())
                .category(request.getCategory())
                .severity(request.getSeverity())
                .likelihood(request.getLikelihood())
                .score(request.getScore())
                .owner(request.getOwner())
                .status(request.getStatus())
                .project(project)
                .build();

        return riskItemRepository.save(risk);
    }

    public List<RiskItem> getAllRisks() {
        return riskItemRepository.findAll();
    }

    public List<RiskItem> getRisksByProject(Long projectId) {
        return riskItemRepository.findByProjectId(projectId);
    }

    public void deleteRisk(Long id) {
        riskItemRepository.deleteById(id);
    }
}