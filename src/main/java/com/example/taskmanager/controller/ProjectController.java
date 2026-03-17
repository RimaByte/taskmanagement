package com.example.taskmanager.controller;

import java.util.List;

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

import com.example.taskmanager.dto.ProjectRequestDto;
import com.example.taskmanager.dto.ProjectResponseDto;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.exception.UserNotFound;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserRepository userRepository;

    public ProjectController(ProjectService projectService, UserRepository userRepository) {
        this.projectService = projectService;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFound("User not found: " + username));
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(@RequestBody ProjectRequestDto dto) {
        User user = getCurrentUser();
        ProjectResponseDto response = projectService.createProject(dto, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> getProject(@PathVariable Long projectId) {
        User user = getCurrentUser();
        ProjectResponseDto response = projectService.showProject(projectId, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        User user = getCurrentUser();
        List<ProjectResponseDto> response = projectService.showAllProjects(user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable Long projectId,
                                                            @RequestBody ProjectRequestDto dto) {
        User user = getCurrentUser();
        ProjectResponseDto response = projectService.editProject(dto, user, projectId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        User user = getCurrentUser();
        projectService.deleteProject(projectId, user);
        return ResponseEntity.noContent().build();
    }
}
