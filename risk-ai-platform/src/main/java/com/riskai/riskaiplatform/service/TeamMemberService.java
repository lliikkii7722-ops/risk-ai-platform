package com.riskai.riskaiplatform.service;

import com.riskai.riskaiplatform.dto.TeamMemberRequest;
import com.riskai.riskaiplatform.entity.Project;
import com.riskai.riskaiplatform.entity.TeamMember;
import com.riskai.riskaiplatform.exception.ProjectNotFoundException;
import com.riskai.riskaiplatform.repository.ProjectRepository;
import com.riskai.riskaiplatform.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final ProjectRepository projectRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository,
                             ProjectRepository projectRepository) {
        this.teamMemberRepository = teamMemberRepository;
        this.projectRepository = projectRepository;
    }

    public TeamMember addTeamMember(TeamMemberRequest request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + request.getProjectId()));

        TeamMember member = TeamMember.builder()
                .name(request.getName())
                .role(request.getRole())
                .email(request.getEmail())
                .availableHours(request.getAvailableHours())
                .allocatedHours(request.getAllocatedHours())
                .project(project)
                .build();

        return teamMemberRepository.save(member);
    }

    public List<TeamMember> getAllTeamMembers() {
        return teamMemberRepository.findAll();
    }

    public List<TeamMember> getTeamMembersByProject(Long projectId) {
        return teamMemberRepository.findByProjectId(projectId);
    }

    public String deleteTeamMember(Long id) {
        teamMemberRepository.deleteById(id);
        return "Team member deleted successfully";
    }
}