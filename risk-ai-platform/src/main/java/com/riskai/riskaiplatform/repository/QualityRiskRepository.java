package com.riskai.riskaiplatform.repository;

import com.riskai.riskaiplatform.entity.QualityRisk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QualityRiskRepository extends JpaRepository<QualityRisk, Long> {
    List<QualityRisk> findByProjectId(Long projectId);
}