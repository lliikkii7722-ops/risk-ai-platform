package com.riskai.riskaiplatform.service;

import com.riskai.riskaiplatform.dto.TaskRequest;
import com.riskai.riskaiplatform.entity.*;
import com.riskai.riskaiplatform.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    private final ProjectTaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TeamMemberRepository teamMemberRepository;

    public ProjectTaskService(
            ProjectTaskRepository taskRepository,
            ProjectRepository projectRepository,
            TeamMemberRepository teamMemberRepository) {

        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    public ProjectTask createTask(TaskRequest request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        TeamMember member = teamMemberRepository.findById(request.getTeamMemberId())
                .orElseThrow(() -> new RuntimeException("Team member not found"));

        ProjectTask task = ProjectTask.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .status(TaskStatus.TODO)
                .project(project)
                .assignedTo(member)
                .build();

        return taskRepository.save(task);
    }

    public List<ProjectTask> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<ProjectTask> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public ProjectTask updateStatus(Long taskId, TaskStatus status) {

        ProjectTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(status);

        return taskRepository.save(task);
    }

    public String deleteTask(Long taskId) {

        taskRepository.deleteById(taskId);

        return "Task deleted successfully";
    }
}