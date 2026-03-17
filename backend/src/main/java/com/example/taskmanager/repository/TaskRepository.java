package com.example.taskmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.taskmanager.entity.Project;
import com.example.taskmanager.entity.Task;

public interface TaskRepository extends CrudRepository<Task, Long>{
    
     Optional<Task> findByIdAndProject(Long TaskId, Project project);
    List<Task> findByProject(Project project);
}
