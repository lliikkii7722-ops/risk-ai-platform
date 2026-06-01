package com.riskai.riskaiplatform.controller;

import com.riskai.riskaiplatform.dto.TaskRequest;
import com.riskai.riskaiplatform.entity.ProjectTask;
import com.riskai.riskaiplatform.entity.TaskStatus;
import com.riskai.riskaiplatform.service.ProjectTaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class ProjectTaskController {

    private final ProjectTaskService taskService;

    public ProjectTaskController(ProjectTaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ProjectTask createTask(@RequestBody TaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping
    public List<ProjectTask> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/project/{projectId}")
    public List<ProjectTask> getTasksByProject(@PathVariable Long projectId) {
        return taskService.getTasksByProject(projectId);
    }

    @PutMapping("/{taskId}/status")
    public ProjectTask updateStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus status) {

        return taskService.updateStatus(taskId, status);
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTask(taskId);
    }
}