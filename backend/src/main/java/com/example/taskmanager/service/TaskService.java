package com.example.taskmanager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.entity.Priority;
import com.example.taskmanager.entity.Project;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.exception.TaskNotFound;
import com.example.taskmanager.mapper.TaskMapper;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.ProjectRepository;

@Service
public class TaskService {
    

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, TaskMapper taskMapper){
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }
    
    public TaskResponseDto createTask(TaskRequestDto dto, Project project){

        

            Task task = taskMapper.toEntity(dto, project);
            taskRepository.save(task);

            return taskMapper.toResponseDto(task);

    }

    public TaskResponseDto showTask(Long taskId, Project project){
        Task task = taskRepository.findByIdAndProject(taskId, project).orElseThrow(() -> new TaskNotFound("Task with ID " + taskId + " not found"));
        return taskMapper.toResponseDto(task);
    }

    public List<TaskResponseDto> showAllTasks(Project project){

        List<Task> tasks = taskRepository.findByProject(project);
        return tasks.stream().map(taskMapper::toResponseDto).collect(Collectors.toList());
    }

    public TaskResponseDto editTask(TaskRequestDto dto, Project project, Long taskId){ 
        
        Task task = taskRepository.findByIdAndProject(taskId, project).orElseThrow(() -> new TaskNotFound("Task with ID " + taskId + " not found"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setTaskStatus(TaskStatus.valueOf(dto.getStatus().toUpperCase()));
        task.setPriority(Priority.valueOf(dto.getPriority().toUpperCase()));
        task.setDueDate(dto.getDueDate());
        taskRepository.save(task);

        return taskMapper.toResponseDto(task);
    
    }

    public void deleteTask(Long taskId, Project project){
        
        Task task = taskRepository.findByIdAndProject(taskId, project).orElseThrow(() -> new TaskNotFound("Task with ID " + taskId + " not found"));
        taskRepository.delete(task);

    }

}
