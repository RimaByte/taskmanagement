package com.example.taskmanager.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.taskmanager.entity.Project;
import com.example.taskmanager.entity.User;
import java.util.List;
import java.util.Optional;


public interface ProjectRepository extends CrudRepository<Project, Long>{

    Optional<Project> findByIdAndOwnerId(Long projectId, User owner);
    List<Project> findByOwner(User owner);

    
}
