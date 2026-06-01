package com.riskai.riskaiplatform.repository;

import com.riskai.riskaiplatform.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}