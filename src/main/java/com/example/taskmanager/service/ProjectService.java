package com.example.taskmanager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.taskmanager.dto.ProjectRequestDto;
import com.example.taskmanager.dto.ProjectResponseDto;
import com.example.taskmanager.entity.Project;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.exception.UserNotFound;
import com.example.taskmanager.mapper.ProjectMapper;
import com.example.taskmanager.repository.ProjectRepository;
import com.example.taskmanager.repository.UserRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, ProjectMapper projectMapper){
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
    }
    
    public ProjectResponseDto createProject(ProjectRequestDto dto, Long id){

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User with ID " + id + " not found"));
        

            Project project = projectMapper.toEntity(dto, user);
            projectRepository.save(project);

            return projectMapper.toResponseDto(project);

    }

    public ProjectResponseDto showProject(Long projectId, User user){
        Project project = projectRepository.findByIdAndOwnerId(projectId, user).orElseThrow(() -> new UserNotFound("Project with ID " + projectId + " not found"));
        return projectMapper.toResponseDto(project);
    }

    public List<ProjectResponseDto> showAllProjects(User user){

        List<Project> projects = projectRepository.findByOwner(user);
        return projects.stream().map(projectMapper::toResponseDto).collect(Collectors.toList());
    }

    public ProjectResponseDto editProject(ProjectRequestDto dto, User user, Long projectId){ 
        
        Project project = projectRepository.findByIdAndOwnerId(projectId, user).orElseThrow(() -> new UserNotFound("Project with ID " + projectId + " not found"));

        
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        projectRepository.save(project);

        return projectMapper.toResponseDto(project);
    
    }

    public void deleteProject(Long projectId, User user){
        
        Project project = projectRepository.findByIdAndOwnerId(projectId, user).orElseThrow(() -> new UserNotFound("Project with ID " + projectId + " not found"));
        projectRepository.delete(project);

    }

}
