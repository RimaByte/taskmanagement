package com.example.taskmanager.mapper;

import org.springframework.stereotype.Component;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.entity.Priority;
import com.example.taskmanager.entity.Project;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequestDto dto, Project projectId){
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setTaskStatus(TaskStatus.valueOf(dto.getStatus().toUpperCase()));
        task.setPriority(Priority.valueOf(dto.getPriority().toUpperCase()));
        task.setDueDate(dto.getDueDate());
        task.setProject(projectId);

        return task;
    }

    public TaskResponseDto toResponseDto(Task task){

        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getTaskStatus().toString());
        dto.setPriority(task.getPriority().toString());
        dto.setDueDate(task.getDueDate());
        dto.setProjectId(task.getProject().getId());

        return dto;
    }


}
