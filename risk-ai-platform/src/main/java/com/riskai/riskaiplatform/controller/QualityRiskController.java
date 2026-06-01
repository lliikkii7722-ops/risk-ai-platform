package com.riskai.riskaiplatform.controller;

import com.riskai.riskaiplatform.dto.QualityRiskRequest;
import com.riskai.riskaiplatform.entity.QualityRisk;
import com.riskai.riskaiplatform.service.QualityRiskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quality-risks")
public class QualityRiskController {

    private final QualityRiskService qualityRiskService;

    public QualityRiskController(QualityRiskService qualityRiskService) {
        this.qualityRiskService = qualityRiskService;
    }

    @PostMapping
    public QualityRisk createQualityRisk(@RequestBody QualityRiskRequest request) {
        return qualityRiskService.createQualityRisk(request);
    }

    @GetMapping
    public List<QualityRisk> getAllQualityRisks() {
        return qualityRiskService.getAllQualityRisks();
    }

    @GetMapping("/project/{projectId}")
    public List<QualityRisk> getQualityRisksByProject(@PathVariable Long projectId) {
        return qualityRiskService.getQualityRisksByProject(projectId);
    }

    @DeleteMapping("/{id}")
    public String deleteQualityRisk(@PathVariable Long id) {
        return qualityRiskService.deleteQualityRisk(id);
    }
}