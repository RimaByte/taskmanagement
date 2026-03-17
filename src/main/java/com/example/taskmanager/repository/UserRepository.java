package com.example.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.taskmanager.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
