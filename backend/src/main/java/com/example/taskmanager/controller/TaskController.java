package com.example.taskmanager.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.entity.Project;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.exception.ProjectNotFound;
import com.example.taskmanager.exception.UserNotFound;
import com.example.taskmanager.repository.ProjectRepository;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.TaskService;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskController(TaskService taskService, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskService = taskService;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFound("User not found: " + username));
    }

    private Project getProject(Long projectId, User user) {
        return projectRepository.findByIdAndOwner(projectId, user)
                .orElseThrow(() -> new ProjectNotFound("Project with ID " + projectId + " not found"));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@PathVariable Long projectId,
                                                      @Valid @RequestBody TaskRequestDto dto) {
        User user = getCurrentUser();
        Project project = getProject(projectId, user);
        TaskResponseDto response = taskService.createTask(dto, project);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable Long projectId,
                                                   @PathVariable Long taskId) {
        User user = getCurrentUser();
        Project project = getProject(projectId, user);
        TaskResponseDto response = taskService.showTask(taskId, project);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(@PathVariable Long projectId) {
        User user = getCurrentUser();
        Project project = getProject(projectId, user);
        List<TaskResponseDto> response = taskService.showAllTasks(project);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long projectId,
                                                      @PathVariable Long taskId,
                                                      @Valid @RequestBody TaskRequestDto dto) {
        User user = getCurrentUser();
        Project project = getProject(projectId, user);
        TaskResponseDto response = taskService.editTask(dto, project, taskId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long projectId,
                                           @PathVariable Long taskId) {
        User user = getCurrentUser();
        Project project = getProject(projectId, user);
        taskService.deleteTask(taskId, project);
        return ResponseEntity.noContent().build();
    }
}
