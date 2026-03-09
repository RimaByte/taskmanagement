package com.example.taskmanager.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.taskmanager.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
}
