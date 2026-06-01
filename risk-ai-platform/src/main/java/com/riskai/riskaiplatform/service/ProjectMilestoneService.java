package com.riskai.riskaiplatform.service;

import com.riskai.riskaiplatform.dto.MilestoneRequest;
import com.riskai.riskaiplatform.entity.Project;
import com.riskai.riskaiplatform.entity.ProjectMilestone;
import com.riskai.riskaiplatform.repository.ProjectMilestoneRepository;
import com.riskai.riskaiplatform.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectMilestoneService {

    private final ProjectMilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    public ProjectMilestoneService(ProjectMilestoneRepository milestoneRepository,
                                   ProjectRepository projectRepository) {
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
    }

    public ProjectMilestone createMilestone(MilestoneRequest request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        ProjectMilestone milestone = ProjectMilestone.builder()
                .milestoneName(request.getMilestoneName())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .progressPercentage(request.getProgressPercentage())
                .status(request.getStatus())
                .project(project)
                .build();

        return milestoneRepository.save(milestone);
    }

    public List<ProjectMilestone> getAllMilestones() {
        return milestoneRepository.findAll();
    }

    public List<ProjectMilestone> getMilestonesByProject(Long projectId) {
        return milestoneRepository.findByProjectId(projectId);
    }

    public String deleteMilestone(Long id) {
        milestoneRepository.deleteById(id);
        return "Milestone deleted successfully";
    }
}