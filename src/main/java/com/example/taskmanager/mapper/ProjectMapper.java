package com.example.taskmanager.mapper;

import org.springframework.stereotype.Component;

import com.example.taskmanager.dto.ProjectRequestDto;
import com.example.taskmanager.dto.ProjectResponseDto;
import com.example.taskmanager.entity.Project;
import com.example.taskmanager.entity.User;

@Component
public class ProjectMapper {
 
    public Project toEntity(ProjectRequestDto dto, User owner){

        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setOwner(owner);
        return project;

    }

    public ProjectResponseDto toResponseDto(Project project){
        ProjectResponseDto dto = new ProjectResponseDto();
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setOwnerId(project.getOwner().getId());
        return dto;
    }
}
