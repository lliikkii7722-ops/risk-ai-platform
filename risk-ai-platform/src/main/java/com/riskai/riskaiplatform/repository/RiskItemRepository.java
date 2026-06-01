package com.riskai.riskaiplatform.repository;

import com.riskai.riskaiplatform.entity.RiskItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiskItemRepository extends JpaRepository<RiskItem, Long> {
    List<RiskItem> findByProjectId(Long projectId);
}