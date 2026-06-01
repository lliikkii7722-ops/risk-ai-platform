package com.riskai.riskaiplatform.controller;

import com.riskai.riskaiplatform.dto.RiskItemRequest;
import com.riskai.riskaiplatform.entity.RiskItem;
import com.riskai.riskaiplatform.service.RiskItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risks")
public class RiskItemController {

    private final RiskItemService riskItemService;

    public RiskItemController(RiskItemService riskItemService) {
        this.riskItemService = riskItemService;
    }

    @PostMapping
    public RiskItem createRisk(@RequestBody RiskItemRequest request) {
        return riskItemService.createRisk(request);
    }

    @GetMapping
    public List<RiskItem> getAllRisks() {
        return riskItemService.getAllRisks();
    }

    @GetMapping("/project/{projectId}")
    public List<RiskItem> getRisksByProject(@PathVariable Long projectId) {
        return riskItemService.getRisksByProject(projectId);
    }

    @DeleteMapping("/{id}")
    public String deleteRisk(@PathVariable Long id) {
        riskItemService.deleteRisk(id);
        return "Risk deleted successfully";
    }
}