package com.riskai.riskaiplatform.controller;

import com.riskai.riskaiplatform.dto.TeamMemberRequest;
import com.riskai.riskaiplatform.entity.TeamMember;
import com.riskai.riskaiplatform.service.TeamMemberService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team-members")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @PostMapping
    public TeamMember addTeamMember(@Valid @RequestBody TeamMemberRequest request) {
        return teamMemberService.addTeamMember(request);
    }

    @GetMapping
    public List<TeamMember> getAllTeamMembers() {
        return teamMemberService.getAllTeamMembers();
    }

    @GetMapping("/project/{projectId}")
    public List<TeamMember> getTeamMembersByProject(@PathVariable Long projectId) {
        return teamMemberService.getTeamMembersByProject(projectId);
    }

    @DeleteMapping("/{id}")
    public String deleteTeamMember(@PathVariable Long id) {
        return teamMemberService.deleteTeamMember(id);
    }
}