package com.riskai.riskaiplatform.repository;

import com.riskai.riskaiplatform.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectId(Long projectId);

}